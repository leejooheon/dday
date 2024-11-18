import Foundation
import SwiftUICore

extension UserDefaults {
    internal static var shared: UserDefaults {
        let appGroupId = "group.com.jooheon.dday"
        return UserDefaults(suiteName: appGroupId)!
    }
}

extension String {
    internal func toItem() -> WidgetDdayModel? {
        guard let data = self.data(using: .utf8) else { return nil }
        do {
            let item = try JSONDecoder().decode(WidgetDdayModel.self, from: data)
            return item
        } catch {
            print("Decoding failed:", error)
            return nil
        }
    }
}

extension Int64 {
    func toColor() -> Color {
        let red = CGFloat((self >> 16) & 0xFF) / 255.0
        let green = CGFloat((self >> 8) & 0xFF) / 255.0
        let blue = CGFloat(self & 0xFF) / 255.0
        
        return Color(red: red, green: green, blue: blue)
    }
}
