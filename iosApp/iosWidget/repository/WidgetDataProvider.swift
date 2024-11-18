import WidgetKit
import SwiftUI

struct WidgetDataProvider: TimelineProvider {
    func placeholder(in context: Context) -> DdayEntry {
        DdayEntry.default
    }

    func getSnapshot(in context: Context, completion: @escaping (DdayEntry) -> ()) {
        completion(DdayEntry.default)
    }

    func getTimeline(in context: Context, completion: @escaping (Timeline<DdayEntry>) -> ()) {
        let currentDate = Date()
        let calendar = Calendar.current

        // 다음 업데이트 시간을 00:00으로 설정
        let nextUpdate = calendar.nextDate(
            after: currentDate,
            matching: DateComponents(hour: 0, minute: 0),
            matchingPolicy: .nextTime
        )!

        let models = UserDefaultRepository.getAll()
        let entry = DdayEntry(date: nextUpdate, models: models)
        
        let timeline = Timeline(entries: [entry], policy: .atEnd)
        completion(timeline)
    }
}
