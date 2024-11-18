import Foundation
import SwiftUI
import shared

struct DdayItem: View {
    var item: Dday
    var colorRaw: Int64
    var onCheckedChange: (Bool) -> Void
    var onItemClick: () -> Void
    var onLongClick: () -> Void
    
    var body: some View {
        HStack {
            Text(item.emoji)
                .font(.system(size: 30))
                .padding(.trailing, 8)
            
            VStack(alignment: .leading) {
                Text(item.title)
                    .font(.title3)
                    .foregroundColor(colorRaw.toColor())
                
                Text(
                    [item.annualUntilDateFormat(), item.annualDdayFormat()]
                        .filter { !$0.isEmpty }
                        .joined(separator: " | ")
                )
                .font(.subheadline.weight(.bold))
                .foregroundColor(Color.primary.opacity(0.85))
                
                if !item.annualEvent && item.isPastYear() {
                    Text("\(item.fullUntilDateFormat()) | \(item.fullDdayFormat())")
                        .font(.subheadline.weight(.black))
                        .foregroundColor(Color.primary.opacity(0.85))
                }
                
                Text(item.dateFormat())
                    .font(.body)
                    .foregroundColor(Color.primary.opacity(0.85))
            }
            
            Spacer()
            
            Toggle(
                "",
                isOn: Binding(
                    get: { item.selected },
                    set: { newValue in onCheckedChange(newValue) }
                )
            )
            .toggleStyle(SwitchToggleStyle())
        }
        .padding()
        .background(Color(UIColor.systemBackground))
        .cornerRadius(8)
        .onLongPressGesture { onLongClick() }
        .onTapGesture { onItemClick() }
    }
}

struct DdayItem_Previews: PreviewProvider {
    static var previews: some View {
        DdayItem(
            item: DdayConstant().previewList().first!,
            colorRaw: 0x0000FF,
            onCheckedChange: { _ in },
            onItemClick: {},
            onLongClick: {}
        )
        .previewLayout(.sizeThatFits)
        .padding()
    }
}
