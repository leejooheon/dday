import SwiftUI

struct DdayText: View {
    let text: String
    let fontSize: CGFloat
    let color: Color
    let fontWeight: Font.Weight
    
    init(
        text: String,
        fontSize: CGFloat,
        color: Color = .primary,
        fontWeight: Font.Weight = .regular
    ) {
        self.text = text
        self.fontSize = fontSize
        self.color = color
        self.fontWeight = fontWeight
    }
    
    var body: some View {
        Text(text)
            .font(.system(size: fontSize, weight: fontWeight))
            .foregroundColor(color)
    }
}
