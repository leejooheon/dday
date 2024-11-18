import Foundation

struct WidgetDdayModel: Codable, Hashable {
    let title: String
    let emoji: String
    let formattedDate: String
    let formattedDday: String
    let colorInt: Int64
    
    static var `default`: WidgetDdayModel {
        return WidgetDdayModel(
            title: "Default Title",
            emoji: "😊",
            formattedDate: "x년 y개월 z일",
            formattedDday: "(D+99))",
            colorInt: 0x0000FF
        )
    }
}
