package com.crossover.common;

import com.relevantcodes.extentreports.ExtentReports;

public class ReportFunction {
    /**
     * Generate the Extent Reports
     *
     * @param filePath of the Report
     * @return report
     * @author Mahesh akoju
     */
    public static ExtentReports getInstance(String filePath) {
        ExtentReports report;
        report = new ExtentReports(filePath, false);
        report.addSystemInfo("Selenium Version", "3.0");
        report.addSystemInfo("Platform", "Windows 10");
        return report;
    }
}
