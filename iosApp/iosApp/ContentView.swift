import UIKit
import SwiftUI
import ComposeApp

class RouteObserver: ObservableObject {
    @Published var isSplashScreen: Bool = true

    func updateRoute(_ route: String) {
        DispatchQueue.main.async {
            self.isSplashScreen = (route == "splash")
        }
    }
}

struct ComposeView: UIViewControllerRepresentable {
    @ObservedObject var routeObserver: RouteObserver

    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController(onRouteChanged: routeObserver.updateRoute)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    @StateObject private var routeObserver = RouteObserver()

    var body: some View {
        ZStack {
            Color(red: 0.847, green: 0.133, blue: 0.424)
                .ignoresSafeArea(edges: routeObserver.isSplashScreen ? .all : .top)
            ComposeView(routeObserver: routeObserver)
        }
    }
}