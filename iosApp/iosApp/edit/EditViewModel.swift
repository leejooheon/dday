import shared
import Foundation
import Combine

extension EditScreen {
    @MainActor class EditViewModel: ObservableObject {
        private var dataSource: DdayDataSource? = nil
        private var ddayId: Int64 = 0
        
        @Published var uiState: Dday = DdayConstant().default()
        let escapeSubject = PassthroughSubject<Void, Never>()
        
        init() {
            let databaseModule = LocalDataModule()
            self.dataSource = databaseModule.ddayDataSource
        }
        
        func loadData(id: Int64) {
            dataSource?.getAllDdays(
                completionHandler: { items, error in
                    let defaultDday = DdayConstant().default()
                    let items = items ?? [] // nil일 경우 빈 배열로 처리
                    let dday = items.first(where: { $0.id == id }) ?? defaultDday
                    
                    self.ddayId = if dday == defaultDday {
                        (items.max { $0.id < $1.id }?.id ?? 0) + 1
                    } else {
                        id
                    }
                    appLog(message: "targetId: \(self.ddayId)")
                    self.uiState = dday
                }
            )
        }
        
        
        func insert(model: Dday) {
            let dday = Dday(
                id: ddayId,
                title: model.title,
                emoji: model.emoji,
                annualEvent: model.annualEvent,
                selected: model.selected,
                date:model.date
            )
            
            dataSource?.insertDday(
                dday: dday,
                completionHandler: { error in
                    self.escapeSubject.send()
                }
            )
        }
    }
}
