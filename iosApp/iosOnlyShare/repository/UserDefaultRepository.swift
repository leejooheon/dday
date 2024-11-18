import Foundation

struct UserDefaultRepository {
    private static let KEY = "all"
    static func getAll() ->[WidgetDdayModel] {
        let items = UserDefaults.shared.stringArray(forKey: KEY) ?? []
        let models = items.compactMap { json in json.toItem() }
        return models
    }
    static func setAll(list: [String]) {
        UserDefaults.shared.set(list, forKey: KEY)
    }
}
