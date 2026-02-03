package vcti.nms.vnms.service;

import org.springframework.stereotype.Service;

@Service
public class ReportFormatter {

    public String formatIncidentReportHtml(String aiReportText, long criticalCount) {

        return """
                <html>
                <head>
                    <style>
                        body { font-family: Arial; padding: 20px; }
                        h2 { color: darkred; }
                        .box {
                            border: 2px solid red;
                            padding: 15px;
                            border-radius: 10px;
                            background: #fff5f5;
                        }
                        pre {
                            background: #f4f4f4;
                            padding: 10px;
                            border-radius: 8px;
                        }
                    </style>
                </head>
                <body>

                    <h2>🚨 VNMS Telecom Incident Report</h2>

                    <div class="box">
                        <p><b>Critical Alarm Storm Detected</b></p>
                        <p>Total Critical Alarms (Last 1 Hour): <b>""" + criticalCount +
                    """
                    </b></p>
                    </div>

                    <h3>📄 AI Generated Analysis</h3>

                    <pre>
                """ + aiReportText + """
                    </pre>

                    <p><i>Generated automatically by VNMS AI Agent</i></p>

                </body>
                </html>
                """;
    }
}
