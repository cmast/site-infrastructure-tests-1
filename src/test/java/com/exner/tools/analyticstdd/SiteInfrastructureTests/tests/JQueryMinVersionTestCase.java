package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.fasterxml.jackson.databind.node.TextNode;

public class JQueryMinVersionTestCase extends WebDriverBasedTestCase {
	private final String _minVersion;

	public JQueryMinVersionTestCase(String pageURL, Object params) {
		super(pageURL);

		if (TextNode.class.isAssignableFrom(params.getClass())) {
			_minVersion = ((TextNode) params).asText();
		} else {
			_minVersion = "0";
			throw new IllegalArgumentException("Must define a min version!");
		}

		setName("jQuery min version " + _minVersion + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor.executeScript(
				"if (typeof jQuery !== 'undefined') { return jQuery.fn.jquery } else { return 'unavailable' }");

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionNotOlderThanBaseVersion((String) response, _minVersion);
			assertTrue("jQuery min version should be " + _minVersion, result);
		} else {
			fail("jQuery version not available");
		}

	}

}
