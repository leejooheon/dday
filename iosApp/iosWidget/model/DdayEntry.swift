import Foundation
import WidgetKit

struct DdayEntry: TimelineEntry {
    let date: Date
    let models: [WidgetDdayModel]
    
    static let `default` = DdayEntry(
        date: Date(),
        models: [WidgetDdayModel.default]
    )
}
