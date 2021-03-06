/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package eu.numberfour.n4js.tester.tests.resource;

import static com.google.common.collect.ContiguousSet.create;
import static com.google.common.collect.DiscreteDomain.integers;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Range.closed;
import static eu.numberfour.n4js.tester.domain.TestStatus.ERROR;
import static eu.numberfour.n4js.tester.domain.TestStatus.FAILED;
import static eu.numberfour.n4js.tester.domain.TestStatus.PASSED;
import static eu.numberfour.n4js.tester.domain.TestStatus.SKIPPED;
import static eu.numberfour.n4js.tester.server.HttpServerManager.CONTEXT_ROOT;
import static eu.numberfour.n4js.tester.server.resources.ContentType.END_SESSION;
import static eu.numberfour.n4js.tester.server.resources.ContentType.END_TEST;
import static eu.numberfour.n4js.tester.server.resources.ContentType.PING_TEST;
import static eu.numberfour.n4js.tester.server.resources.ContentType.START_SESSION;
import static eu.numberfour.n4js.tester.server.resources.ContentType.START_TEST;
import static eu.numberfour.n4js.tester.server.resources.HttpMethod.POST;
import static eu.numberfour.n4js.tester.server.resources.ResourceRouterServlet.CONTEXT_PATH;
import static eu.numberfour.n4js.tester.tests.TesterConstants.PORT;
import static java.lang.Integer.highestOneBit;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Collections.singletonMap;
import static java.util.UUID.randomUUID;
import static java.util.stream.StreamSupport.stream;
import static org.apache.log4j.Logger.getLogger;
import static org.apache.log4j.Logger.getRootLogger;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.emf.common.util.URI;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import eu.numberfour.n4js.tester.TesterFacade;
import eu.numberfour.n4js.tester.TesterModule;
import eu.numberfour.n4js.tester.domain.ID;
import eu.numberfour.n4js.tester.domain.TestCase;
import eu.numberfour.n4js.tester.domain.TestResult;
import eu.numberfour.n4js.tester.domain.TestStatus;
import eu.numberfour.n4js.tester.domain.TestSuite;
import eu.numberfour.n4js.tester.domain.TestTree;
import eu.numberfour.n4js.tester.server.HttpServerManager;
import eu.numberfour.n4js.tester.server.resources.ContentType;
import eu.numberfour.n4js.tester.server.resources.HttpMethod;
import eu.numberfour.n4js.tester.tests.InjectedModules;
import eu.numberfour.n4js.tester.tests.JUnitGuiceClassRunner;

/**
 * Test for a mock test session.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = {})
public class MockTest {

	private static final Logger LOGGER = getLogger(MockTest.class);
	private static final boolean DEBUG = false;

	private static final int SERIAL_TEST_CASE_COUNT_FACTOR = 100;
	private static final int PARALLEL_TEST_CASE_COUNT_FACTOR = 100;
	private static final String URL = "http://localhost:" + PORT + CONTEXT_ROOT + CONTEXT_PATH;

	@Inject
	private TesterFacade facade;

	@Inject
	private HttpServerManager serverManager;

	@Inject
	private ObjectMapper mapper;

	/***/
	@BeforeClass
	public static void beforeClass() {
		if (!DEBUG) {
			getRootLogger().removeAllAppenders();
			getRootLogger().addAppender(new NullAppender());
		}
	}

	/***/
	@Before
	public void before() {
		serverManager.stopServer(PORT);
	}

	/***/
	@After
	public void after() {
		serverManager.stopServer(PORT);
	}

	/**
	 * Mocks a test session in parallel test execution fashion.
	 */
	@Test
	public void testParallelMock() {
		testMock(true, PARALLEL_TEST_CASE_COUNT_FACTOR);
	}

	/**
	 * Mocks a test session in serial test execution fashion.
	 */
	@Test
	public void testSerialMock() {
		testMock(false, SERIAL_TEST_CASE_COUNT_FACTOR);
	}

	private void testMock(final boolean parallel, int testCaseCountFactor) {
		final String sessionId = valueOf(randomUUID());
		final TestTree testTree = createNewTestTree(sessionId, testCaseCountFactor);
		facade.prepareTestSession(testTree);
		testMock(testTree, parallel);
	}

	private Stream<TestCase> getTestCaseStream(final TestTree tree, final boolean parallel) {
		return stream(tree.spliterator(), parallel);
	}

	/**
	 * Mocks the {@link TestTree test tree} argument with fake test results.
	 *
	 * @param testTree
	 *            the test tree to generate test result.
	 * @param parallel
	 *            {@code true} if the mock test should be executed in parallel fashion. For serial execution this flag
	 *            is {@code false}.
	 */
	public void testMock(final TestTree testTree, final boolean parallel) {

		final String sessionId = testTree.getSessionId().getValue();

		final String mode = parallel ? "parallel" : "synchronous";
		log("Starting " + mode + " mock test session.");
		server(URL + sessionId + "/start/", START_SESSION, POST, null);

		final AtomicInteger i = new AtomicInteger();
		final AtomicInteger percentage = new AtomicInteger();
		final int numberOfTests = size(testTree);

		getTestCaseStream(testTree, parallel).forEach(new Consumer<TestCase>() {

			@Override
			public void accept(final TestCase testCase) {
				final String testId = testCase.getId().getValue();
				if (0 < numberOfTests && 0 < i.get()) {
					final int value = (int) (((double) i.get() / (double) numberOfTests) * 100.0);
					if (value > percentage.get()) {
						synchronized (MockTest.class) {
							if (value > percentage.get()) {
								percentage.set(value);
								log(format("Running " + mode + " mock tests... [%2s%%]", value));
							}
						}
					}
				}
				server(URL + sessionId + "/tests/" + testId + "/start/", START_TEST, POST, createTimeoutBody(5_000L));
				final long timeout = getMockTestExecutionTime(i.get());
				server(URL + sessionId + "/tests/" + testId + "/ping/", PING_TEST, POST,
						createTimeoutBody(timeout + 5_000L));
				server(URL + sessionId + "/tests/" + testId + "/end/", END_TEST, POST,
						createTestResult(timeout, i.incrementAndGet()));
			}
		});

		log("Ending " + mode + " mock test session.");
		server(URL + sessionId + "/end/", END_SESSION, POST, null);
	}

	private void log(String msg) {
		if (DEBUG) {
			LOGGER.info(msg);
		} else {
			System.out.println(msg);
		}
	}

	private void server(final String url, final ContentType contentType, final HttpMethod method, final Object body) {
		HttpURLConnection req = null;
		try {
			req = (HttpURLConnection) new URL(url).openConnection();
			req.setRequestMethod(valueOf(method));
			req.setRequestProperty("Content-Type", valueOf(contentType));
			req.setDoInput(true);
			req.setDoOutput(true);
			req.setConnectTimeout(0);
			req.setReadTimeout(0);
			if (null != body) {
				try (final OutputStream os = req.getOutputStream();
						final OutputStreamWriter osw = new OutputStreamWriter(os)) {
					osw.write(mapper.writeValueAsString(body));
					osw.flush();
				}
			}
			assertThat(url, getResponseCode(req), equalTo(200));
		} catch (final Exception e) {
			LOGGER.error("Error while performing HTTP " + method + " to " + url + ".", e);
			throw new RuntimeException("Error while performing HTTP " + method + " to " + url + ".", e);
		} finally {
			if (null != req) {
				req.disconnect();
			}
		}
		req = null;
	}

	private int getResponseCode(final HttpURLConnection req) throws IOException {

		/*
		 * Ensure that we have connected to the server. Record exception as we need to re-throw it if there isn't a
		 * status line.
		 */
		Exception exc = null;
		try (InputStream is = req.getInputStream()) {
			//
		} catch (final Exception e) {
			exc = e;
		}

		/*
		 * If we can't a status-line then re-throw any exception that getInputStream threw.
		 */
		final String statusLine = req.getHeaderField(0);
		if (statusLine == null) {
			if (exc != null) {
				if (exc instanceof RuntimeException)
					throw (RuntimeException) exc;
				else
					throw (IOException) exc;
			}
			return -1;
		}

		/*
		 * Examine the status-line - should be formatted as per section 6.1 of RFC 2616 :-
		 *
		 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase
		 *
		 * If status line can't be parsed return -1.
		 */
		if (statusLine.startsWith("HTTP/1.")) {
			final int codePos = statusLine.indexOf(' ');
			if (codePos > 0) {

				int phrasePos = statusLine.indexOf(' ', codePos + 1);

				// deviation from RFC 2616 - don't reject status line
				// if SP Reason-Phrase is not included.
				if (phrasePos < 0)
					phrasePos = statusLine.length();

				try {
					final int responseCode = Integer.parseInt(statusLine.substring(codePos + 1, phrasePos));
					return responseCode;
				} catch (final NumberFormatException e) {
					//
				}
			}
		}
		return -1;
	}

	private long getMockTestExecutionTime(final int i) {
		return 1L * i % 5;
	}

	private Object createTestResult(final long timeout, final int i) {
		final String expected = valueOf(i);
		final String actual;
		final TestStatus status;
		if (0 == i % 19) {
			status = FAILED;
			actual = "mod 19 for " + i;
		} else if (0 == i % 31) {
			status = ERROR;
			actual = "mod 31 for " + i;
		} else if (highestOneBit(i) == i) {
			actual = "power of 2 for " + i;
			status = SKIPPED;
		} else {
			actual = expected;
			status = PASSED;
		}
		final TestResult result = new TestResult(status);
		result.setElapsedTime(timeout);
		result.setActual(actual);
		result.setExpected(expected);
		return result;
	}

	private Object createTimeoutBody(final long timeout) {
		return singletonMap("timeout", timeout);
	}

	private TestTree createNewTestTree(final String sessionId, final int testCaseCountFactor) {
		final List<TestSuite> testSuites = newArrayList();
		create(closed(1, testCaseCountFactor), integers()).forEach(
				i -> {
					final String suiteName = format("%05d", i) + "_TestSuite";
					final TestSuite suite = new TestSuite(suiteName);
					create(closed(1, testCaseCountFactor), integers()).forEach(
							j -> {
						final String testCaseId = getTestCaseId(i, j);
						suite.add(new TestCase(
								new ID(testCaseId),
								"origin." + suiteName + "." + testCaseId + ".0.0.1",
								suiteName + "." + testCaseId,
								testCaseId,
								testCaseId, URI.createURI("testURI_" + testCaseId)));
					});
					testSuites.add(suite);
				});
		return new TestTree(new ID(sessionId), testSuites);
	}

	private String getTestCaseId(final Integer i, final Integer j) {
		return new StringBuilder().append(format("%05d", i)).append("_").append(format("%05d", j))
				.append("_TestMethod").toString();
	}

	private Matcher<Integer> equalTo(final int expected) {
		return new BaseMatcher<Integer>() {

			@Override
			public boolean matches(final Object actual) {
				return actual instanceof Integer && ((Integer) actual).intValue() == expected;
			}

			@Override
			public void describeTo(final Description desc) {
				desc.appendText("<" + expected + ">");
			}
		};
	}

}
