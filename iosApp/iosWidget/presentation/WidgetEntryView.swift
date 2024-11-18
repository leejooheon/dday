import WidgetKit
import SwiftUI

struct WidgetEntryView : View {
    var entry: WidgetDataProvider.Entry
    @Environment(\.widgetFamily) var family

    
    var body: some View {
        let count = switch family {
        case .systemMedium: 3
        case .systemLarge: 7
        @unknown default: 0
        }
        
        VStack {
            ForEach(
                entry.models.prefix(count),
                id: \.self
            ) { model in
                DdayItem(model: model)
            }
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .topLeading
        )
        .background(Color(UIColor.clear))
    }
}

struct WidgetEntryView_Previews: PreviewProvider {
    
    static var previews: some View {
        WidgetEntryView(
            entry: DdayEntry(
                date: Date(),
                models: [WidgetDdayModel.default]
            )
        )
    }
}
