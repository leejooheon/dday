import WidgetKit
import SwiftUI

struct iosWidget: Widget {
    let kind = WidgetConstant.kind

    var body: some WidgetConfiguration {
        StaticConfiguration(
            kind: kind,
            provider: WidgetDataProvider()
        ) { entry in
            if #available(iOS 17.0, *) {
                WidgetEntryView(entry: entry)
                    .containerBackground(.fill.tertiary, for: .widget)
            } else {
                WidgetEntryView(entry: entry)
                    .padding()
                    .background()
            }
        }
        .configurationDisplayName(WidgetConstant.displayName)
        .description(WidgetConstant.description)
        .supportedFamilies(
            [.systemMedium, .systemLarge]
        )
    }
}

#Preview(as: .systemSmall) {
    iosWidget()
} timeline: {
//    SimpleEntry(date: .now, emoji: "😀")
//    SimpleEntry(date: .now, emoji: "🤩")
    DdayEntry.default
}
