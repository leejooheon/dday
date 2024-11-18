import SwiftUI
//import MCEmojiPicker

struct DdayItem: View {
    let model: WidgetDdayModel
    
    var body: some View {
        HStack {
            Spacer().frame(width: 8)
            
            DdayText(
                text: model.emoji,
                fontSize: 20
            )
            
            Spacer().frame(width: 8)
            
            DdayText(
                text: model.title,
                fontSize: 12,
                color: model.colorInt.toColor(),
                fontWeight: .bold
            )
            
            Spacer().frame(width: 8)
            
            DdayText(
                text: model.formattedDate,
                fontSize: 10,
                color: Color.primary.opacity(0.7)
            )
            
            Spacer().frame(width: 4)
            
            DdayText(
                text: "(\(model.formattedDday))",
                fontSize: 12,
                color: Color.primary.opacity(0.85)
            )
            
            Spacer().frame(width: 8)
        }
        .frame(
            maxWidth: .infinity,
            alignment: .leading
        )
        .padding(8)
        .background(Color(UIColor.systemBackground))
        .cornerRadius(8)
    }
}
