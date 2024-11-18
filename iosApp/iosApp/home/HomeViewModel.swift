import shared
import Foundation
import Combine
import PhotosUI

extension HomeScreen {
    @MainActor class HomeViewModel: ObservableObject {
        private var dataSource: DdayDataSource? = nil
        private var settingDataStore: SettingDataStore? = nil
        
        private let ddayConstant = DdayConstant()
        
        @Published var uiState: HomeUiState = HomeUiState.default
        let navigateSubject = PassthroughSubject<ScreenNavigation, Never>()
    
        init() {
            let localDataModule = LocalDataModule()
            self.dataSource = localDataModule.ddayDataSource
            self.settingDataStore = localDataModule.settingUserDefault
        }
        
        func loadData() {
            dataSource?.getAllDdays(
                completionHandler: { items, error in
                    let model = items ?? []
                    
                    UserDefaultRepository.setAll(
                        list: model
                            .filter { dday in dday.selected }
                            .enumerated()
                            .compactMap { (index, dday) in
                                self.toWidgetJsonStringOrNil(index: index, dday: dday)
                            }
                    )
                    
                    self.uiState = HomeUiState(
                        profileImageUrl: self.settingDataStore?.getProfileUrl() ?? "",
                        items: model
                    )
                }
            )
        }
        
        func dispatch(event: HomeUiEvent) {
            switch event {
            case .onProfileImageSelected(let uiImage):
                let path = saveImageToLocalDirectory(image: uiImage)
                if let path {
                    self.settingDataStore?.putProfileUrl(profileUrl: path)
                    loadData()
                }
            case .onAddButtonClick:
                navigateSubject.send(.Edit(id: -1))
            case .onDetailClicked(let model):
                navigateSubject.send(.Edit(id: model.id))
            case .onDelete(let id):
                dataSource?.deleteDdayById(
                    id: id,
                    completionHandler: { _ in self.loadData() }
                )
            case .onToggleSelected(let model):
                let dday = Dday(
                    id: model.id,
                    title: model.title,
                    emoji: model.emoji,
                    annualEvent: model.annualEvent,
                    selected: !model.selected,
                    date:model.date
                )

                dataSource?.insertDday(
                    dday: dday,
                    completionHandler: { _ in self.loadData() }
                )
            }
        }
        
        internal func toWidgetJsonStringOrNil(index: Int, dday: Dday) -> String? {
            let ddayFormat = if(dday.annualEvent) { dday.annualDdayFormat()}
                             else { dday.fullDdayFormat() }
            let dateFormat = if(dday.annualEvent) { dday.annualUntilDateFormat()}
                             else { dday.fullUntilDateFormat() }
            
            
            let model = WidgetDdayModel(
                title: dday.title,
                emoji: dday.emoji,
                formattedDate: dateFormat,
                formattedDday: ddayFormat,
                colorInt: self.ddayConstant.getColor(index: Int32(index))
            )
            
            do {
                let jsonData = try JSONEncoder().encode(model)
                let jsonString = String(data: jsonData, encoding: .utf8)!
                return jsonString
            } catch {
                return nil
            }
        }
        
        private func saveImageToLocalDirectory(image: UIImage) -> String? {
            let fileManager = FileManager.default
            guard let documentDirectory = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first else {
                return nil
            }

            // 파일 경로 설정
            let fileUrl = documentDirectory.appendingPathComponent("profileImage.jpg")

            // 이미지를 Data로 변환하여 파일로 저장
            if let imageData = image.jpegData(compressionQuality: 1.0) {
                do {
                    try imageData.write(to: fileUrl)
                    return fileUrl.absoluteString // 저장된 파일 URL 반환
                } catch {
                    print("이미지 저장 실패: \(error)")
                    return nil
                }
            }
            
            return nil
        }
    }
}
