import Foundation
import shared

struct HomeUiState: Equatable {
    var profileImageUrl: String
    var items: [Dday]
    
    // 기본 상태를 제공하는 static 속성
    static let `default` = HomeUiState(
        profileImageUrl: "",
        items: []
    )
}
