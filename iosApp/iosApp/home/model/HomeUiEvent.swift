import Foundation
import PhotosUI
import shared

enum HomeUiEvent {
    case onAddButtonClick
    case onDetailClicked(model: Dday)
    case onDelete(id: Int64)
    case onToggleSelected(model: Dday)
    case onProfileImageSelected(uiImage: UIImage)
}
