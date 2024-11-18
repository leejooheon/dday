import Foundation
import SwiftUI

struct EmptySection: View {
    var body: some View {
        VStack(
            alignment: .center,
            spacing: 16
        ) {
            Text("😱")
                .font(.system(size: 64))
                .padding(.bottom, 16)
            
            Text("no events!")
                .font(.headline)
                .foregroundColor(Color.primary)
                .multilineTextAlignment(.center)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity) // 화면 중앙에 배치
        .padding(.vertical, 16)
    }
}

struct EmptySection_Previews: PreviewProvider {
    static var previews: some View {
        EmptySection()
    }
}
