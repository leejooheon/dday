import SwiftUI

struct TextFieldWithLabel: View {
    var label: String
    var value: String
    var errorLabel: String = ""
    var isError: Bool = false
    var keyboardType: UIKeyboardType = .default
    var onValueChange: (String) -> Void
    
    @FocusState private var isFocused: Bool // Focus 상태 관리

    var body: some View {
        VStack(alignment: .leading) {
            Text(label)
                .foregroundColor(.primary)
                .font(.body)

            TextField("", text: Binding(
                get: { value },
                set: { newValue in onValueChange(newValue) }
            ))
            .textFieldStyle(RoundedBorderTextFieldStyle())
            .padding(.vertical, 8)
            .padding(.horizontal, 16)
            .background(Color.gray.opacity(0.1))
            .cornerRadius(8)
            .keyboardType(keyboardType)
            .focused($isFocused)
            
            // 오류 아이콘
            if isError {
                HStack {
                    Image(systemName: "exclamationmark.circle.fill")
                        .foregroundColor(.red)
                    Text(errorLabel)
                        .foregroundColor(.red)
                        .font(.caption)
                }
            }
        }
        .padding(.horizontal, 16)
    }
}
