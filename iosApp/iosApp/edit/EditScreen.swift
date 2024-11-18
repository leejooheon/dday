import SwiftUI
import shared
import MCEmojiPicker

struct EditScreen: View {
    @StateObject var viewModel = EditViewModel()
    @Environment(\.presentationMode) var presentationMode
    private var selectedId: Int64 = 0
    
    init(selectedId: Int64 = 0) {
        self.selectedId = selectedId
    }
    
    var body: some View {
        VStack {
            EditContentView(
                dday: viewModel.uiState,
                onSave: { dday in
                    viewModel.insert(model: dday)
                }
            )
        }
        .onReceive(viewModel.escapeSubject) { _ in
            presentationMode.wrappedValue.dismiss()
        }
        .onAppear {
            viewModel.loadData(id: selectedId)
        }
    }
}

struct EditContentView: View {
    @State private var title: String = ""
    @State private var dateText: String = ""
    @State private var emoji: String = "😀"
    @State private var annualEvent: Bool = true
    
    @State private var titleError: Bool = false
    @State private var dateError: Bool = false
    @State private var scrollOffset: CGFloat = 0.0
    
    @State private var isPresented: Bool = false
    
    var dday: Dday
    var onSave: (Dday) -> Void
    
    init(dday: Dday, onSave: @escaping (Dday) -> Void) {
        self.dday = dday
        self.onSave = onSave
    }
    
    var body: some View {
        NavigationView {
            VStack {
                ScrollView {
                    VStack {
                        VStack {
                            Text(emoji)
                                .frame(
                                    maxWidth: .infinity,
                                    alignment: .center
                                )
                                .font(.system(size: 128))
                                .foregroundColor(Color.primary)
                        }
                        .onTapGesture { isPresented = true }
                        .background(
                            RoundedRectangle(cornerRadius: 16)
                                .strokeBorder(Color.primary, lineWidth: 2)
                        )
                        .padding()
                        
                        Spacer().frame(height: 16)
                        
                        TextFieldWithLabel(
                            label: "Title",
                            value: title,
                            onValueChange: { value in
                                title = value
                                titleError = value.isEmpty
                            }
                        )
                        
                        Spacer().frame(height: 8)
                        
                        TextFieldWithLabel(
                            label: "Date",
                            value: dateText,
                            errorLabel: "yyyymmdd 형식으로 입력해주세요",
                            isError: dateError,
                            keyboardType: UIKeyboardType.decimalPad,
                            onValueChange: { value in
                                dateText = value
                                dateError = isError(dateString: value)
                            }
                        )
                        
                        Spacer().frame(height: 16)
                        
                        HStack {
                            Text("매년 돌아오는 이벤트 인가요?")
                                .font(.system(size: 16, weight: .bold))
                                .padding(.horizontal, 16)
                                    
                            Spacer().frame(width: 8)
                                    
                            Toggle(isOn: $annualEvent) {
                                Image(systemName: annualEvent ? "checkmark.circle.fill" : "xmark.circle")
                                    .foregroundColor(annualEvent ? .green : .red)
                            }
                            .labelsHidden()
                                    
                            Spacer().frame(width: 16)
                                    
                            Text(annualEvent ? "Yes" : "No")
                        }
                        .frame(
                            maxWidth: .infinity,
                            alignment: .leading
                        )
                        
                        Spacer().frame(height: 32)
                        
                        Button(
                            action: {
                                onSave(
                                    Dday(
                                        id: 0,
                                        title: title,
                                        emoji: emoji,
                                        annualEvent: annualEvent,
                                        selected: true,
                                        date: DdayConstant().yyyymmddToTimeMillis(
                                            dateString: dateText
                                        )
                                    )
                                )
                            }
                        ) {
                            HStack {
                                Image(systemName: "checkmark.circle.fill").font(.title)
                                Text("Save").font(.title2)
                            }
                            .padding()
                            .background(Color.blue)
                            .foregroundColor(.white)
                            .cornerRadius(25)
                        }
                        .frame(alignment: .center)
                        .cornerRadius(25)
                        .disabled(dateError || titleError)
                    }
                    .padding()
                    .emojiPicker(
                        isPresented: $isPresented,
                        selectedEmoji: $emoji
                    )
                }
            }
            .onAppear {
                if dday != DdayConstant().default() {
                    title = dday.title
                    dateText = dday.ddayDateFormat()
                }
                annualEvent = dday.annualEvent
                emoji = dday.emoji
                titleError = title.isEmpty
            }
        }
    }
}


private func isError(dateString: String) -> Bool {
//    return dateString.count != 8
    return dateString.count != 8 || DdayConstant().yyyymmddToTimeMillis(
        dateString: dateString
    ) == 0
}

struct EditScreen_Previews: PreviewProvider {
    static var previews: some View {
        EditContentView(
            dday: DdayConstant().default(),
            onSave: { _ in }
        )
    }
}
