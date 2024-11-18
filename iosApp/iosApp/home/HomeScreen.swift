import SwiftUI
import WidgetKit
import shared

struct HomeScreen: View {
    @StateObject var viewModel = HomeViewModel()
    @State private var isNavigatingToEditScreen = false
    @State private var selectedId: Int64 = -1
    
    var body: some View {
        VStack {
            HomeContentView(
                homeUiState: viewModel.uiState,
                onEvent: { event in
                    viewModel.dispatch(event: event)
                }
            )

            NavigationLink(
                destination: EditScreen(selectedId: selectedId),
                isActive: $isNavigatingToEditScreen
            ) {
                EmptyView()
            }
        }
        .onReceive(viewModel.navigateSubject) { navigation in
            switch navigation {
                case let editNavigation as ScreenNavigation.Edit:
                    selectedId = editNavigation.id
                    isNavigatingToEditScreen = true
                default: break
            }
        }
        .onAppear {
            viewModel.loadData()
        }
        .onChange(of: viewModel.uiState) { newState in
            WidgetCenter.shared.reloadAllTimelines()
        }
    }
}

struct HomeContentView: View {
    let homeUiState: HomeUiState // UI 상태 데이터
    let onEvent: (HomeUiEvent) -> Void // 이벤트 핸들러
    
    let ddayConstant = DdayConstant()
    
    @State private var isShowingDeleteDialog = false
    @State private var idToDelete: Int64 = -1
    
    var body: some View {
        NavigationView {
            VStack {
                if homeUiState.items.isEmpty {
                    EmptySection()
                } else {
                    ScrollView {
                        LazyVStack(spacing: 8) {
                            TopSection(
                                profileImageUrl: homeUiState.profileImageUrl,
                                onProfileImageSelected: { uiImage in
                                    let event = HomeUiEvent.onProfileImageSelected(uiImage: uiImage)
                                    onEvent(event)
                                }
                            )
                            
                            ForEach(homeUiState.items, id: \.id) { item in
                                DdayItem(
                                    item: item,
                                    colorRaw: ddayConstant.getColor(
                                        index: Int32(
                                            homeUiState.items.firstIndex(
                                                where: { $0.id == item.id }
                                            ) ?? 0
                                        )
                                    ),
                                    onCheckedChange: { _ in
                                        let event = HomeUiEvent.onToggleSelected(model: item)
                                        onEvent(event)
                                    },
                                    onItemClick: {
                                        let event = HomeUiEvent.onDetailClicked(model: item)
                                        onEvent(event)
                                    },
                                    onLongClick: {
                                        print("longClick: \(item.id)")
                                        idToDelete = item.id
                                        isShowingDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
                
                Spacer()
                Button(
                    action: {
                        let event = HomeUiEvent.onAddButtonClick
                        onEvent(event)
                    }
                ) {
                    ZStack {
                        Circle()
                            .foregroundColor(Color.blue)  // 버튼 배경 색상
                            .frame(width: 56, height: 56)
                        Image(systemName: "plus")  // 아이콘
                            .foregroundColor(.white)
                            .font(.title)
                    }
                }
                .padding(.bottom, 32)
                .padding(.trailing, 32)
                .frame(maxWidth: .infinity, alignment: .trailing)
            }
        }.navigationBarTitle("My D-days", displayMode: .inline)
        .alert(isPresented: $isShowingDeleteDialog) {
            Alert(
                title: Text("Delete"),
                message: Text("Do you want delete?"),
                primaryButton: .destructive(Text("delete")) {
                    let event = HomeUiEvent.onDelete(id: idToDelete)
                    onEvent(event)
                },
                secondaryButton: .cancel()
            )
        }
        .onChange(of: isShowingDeleteDialog) { showOrHide in
            if !showOrHide { idToDelete = -1 }
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    
    static var previews: some View {
        HomeContentView(
            homeUiState: HomeUiState(
                profileImageUrl: "",
                items: DdayConstant().previewList()
            ),
            onEvent: { _ in }
        )
    }
}
