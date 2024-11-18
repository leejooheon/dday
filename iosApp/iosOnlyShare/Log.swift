import Foundation
import os.log

func appLog(message: String) {
    let logger = Logger(subsystem: "com.jooheon.dday", category: "app")
    logger.debug("\(message)")
}

func widgetLog(message: String) {
    let logger = Logger(subsystem: "com.jooheon.dday", category: "widget")

    logger.debug("\(message)")
}
