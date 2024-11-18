import SwiftUI
import PhotosUI

struct TopSection: View {
    @State private var isImagePickerPresented = false
    var profileImageUrl: String? = nil
    var onProfileImageSelected: (UIImage) -> Void

    var body: some View {
        HStack(alignment: .center) {
            Button(action: {
                isImagePickerPresented = true
            }) {
                if let profileImageUrl = profileImageUrl,
                    let url = URL(string: profileImageUrl),
                    let image = UIImage(contentsOfFile: url.path) {
                    Image(uiImage: image)
                        .resizable()
                        .scaledToFill()
                        .frame(width: 64, height: 64)
                        .clipShape(Circle())
                } else {
                    Image(systemName: "person.circle.fill")
                        .resizable()
                        .scaledToFill()
                        .frame(width: 64, height: 64)
                        .clipShape(Circle())
                        .foregroundColor(.gray)
                }
            }
            .sheet(isPresented: $isImagePickerPresented) {
                ImagePickerView(
                    onImageSelected: onProfileImageSelected
                )
            }

            Spacer().frame(width: 16)

            HStack {
                Image(systemName: "calendar")
                    .font(.title)
                Spacer().frame(width: 8)
                Text("Date Reminder")
                    .font(.title2)
                    .fontWeight(.medium)
            }
        }
        .frame(
            maxWidth: .infinity,
            alignment: .leading
        )
        .padding(16)
    }
}

struct ImagePickerView: UIViewControllerRepresentable {
    var onImageSelected: (UIImage) -> Void
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    func makeUIViewController(context: Context) -> PHPickerViewController {
        var config = PHPickerConfiguration()
        config.filter = .images
        let picker = PHPickerViewController(configuration: config)
        picker.delegate = context.coordinator
        return picker
    }
    
    func updateUIViewController(_ uiViewController: PHPickerViewController, context: Context) {}
    
    class Coordinator: NSObject, PHPickerViewControllerDelegate {
        let parent: ImagePickerView
        
        init(_ parent: ImagePickerView) {
            self.parent = parent
        }
        
        func picker(_ picker: PHPickerViewController, didFinishPicking results: [PHPickerResult]) {
            picker.dismiss(animated: true)
            guard let provider = results.first?.itemProvider, provider.canLoadObject(ofClass: UIImage.self) else {
                return
            }
            provider.loadObject(ofClass: UIImage.self) { [weak self] (image, _) in
                guard let self = self, let uiImage = image as? UIImage else {
                    return
                }
                DispatchQueue.main.async {
                    self.parent.onImageSelected(uiImage)
                }
            }
        }
    }
}


struct TopSection_Previews: PreviewProvider {
    
    static var previews: some View {
        TopSection(
            onProfileImageSelected: { _ in
                
            }
        )
    }
}
