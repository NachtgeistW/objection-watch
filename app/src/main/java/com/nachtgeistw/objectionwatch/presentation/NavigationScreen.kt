import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable

/**
 * Navigation routes enum.
 */
public interface NavigationScreen {
    @Serializable
    public data class Player(val page: Int = 0) : NavigationScreen {
        public companion object {
            public fun deepLinks(deepLinkPrefix: String): List<NavDeepLink> = listOf(
                navDeepLink {
                    uriPattern = "$deepLinkPrefix/player?page={page}"
                },
            )

            public const val Player: Int = 0
            public const val Library: Int = 1
        }
    }

    @Serializable
    public data object Volume : NavigationScreen {
        public fun deepLinks(deepLinkPrefix: String): List<NavDeepLink> = listOf(
            navDeepLink {
                uriPattern = "$deepLinkPrefix/volume"
            },
        )
    }

    @Serializable
    public data class MediaItem(val id: String, val collectionId: String?) : NavigationScreen {
        public companion object {
            public fun deepLinks(deepLinkPrefix: String): List<NavDeepLink> = listOf(
                navDeepLink {
                    uriPattern = "$deepLinkPrefix/mediaItem?id={id}&collectionId={collectionId}"
                },
            )
        }
    }

    @Serializable
    public data object Settings : NavigationScreen {
        public fun deepLinks(deepLinkPrefix: String): List<NavDeepLink> = listOf(
            navDeepLink {
                uriPattern = "$deepLinkPrefix/settings"
            },
        )
    }

    @Serializable
    public data object Collections : NavigationScreen {
        public fun deepLinks(deepLinkPrefix: String): List<NavDeepLink> = listOf(
            navDeepLink {
                uriPattern = "$deepLinkPrefix/collections"
            },
        )
    }

    @Serializable
    public data class Collection(val id: String, val name: String? = null) : NavigationScreen {
        public companion object {
            public fun deepLinks(deepLinkPrefix: String): List<NavDeepLink> = listOf(
                navDeepLink {
                    uriPattern = "$deepLinkPrefix/collection?id={id}&name={name}"
                },
            )
        }
    }
}
