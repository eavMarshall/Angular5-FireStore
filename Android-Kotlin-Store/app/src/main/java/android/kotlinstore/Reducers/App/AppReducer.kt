package android.kotlinstore.Reducers.App

import android.kotlinstore.Store.Action
import android.kotlinstore.Store.Reducer

class AppReducer : Reducer() {
    companion object {
        val name = "AppReducer"
        val LOGIN_PAGE = 0
        val HOME_PAGE = 1
        val SETTING_PAGE = 2
        val ADD_TO_DAY_PAGE = 3

        private val reducerName = "[$name:] "

        private val SET_USER: String = reducerName + "set user user"
        private val SET_DRAW: String = reducerName + "set draw state"

        private val OPEN_FAB_MENU: String = reducerName + "open fab menu"
        private val CLOSE_FAB_MENU: String = reducerName + "close fab menu"
        private val TOGGLE_FAB_MENU: String = reducerName + "toggle fab menu"
        private val SET_FAB_ICON: String = reducerName + "set fab icon"
        private val SET_FAB_STATE: String = reducerName + "set fab state"

        private val SEND_SNACKBAR_MESSAGE: String = reducerName + "open up a snackbar message"

        private val SET_HOME_PAGE: String = reducerName + "set home page"
        private val SET_LOGIN_PAGE: String = reducerName + "set login page"
        private val SET_SETTINGS_PAGE: String = reducerName + "set settings page"
        private val SET_ADD_TO_DAY_PAGE: String = reducerName + "set add to day page"

        private val SET_LOADING: String = reducerName + "set loading state"

        private val SET_DIALOG: String = reducerName + "set dialog state"
    }

    data class State (
            var dialog: DialogState = DialogState(),
            var draw: DrawState = DrawState(),
            var fab: FabState = FabState(),
            var snack: SnackState = SnackState(),
            var toolbar: ToolbarState = ToolbarState(),
            var user: UserState = UserState(),
            var page: PageState = PageState(),
            var isLoading: Boolean = false
    )

    class SET_USER_ACTION(payload: UserState) : Action(type= SET_USER) { var payload = payload }
    class SET_DRAW_ACTION(payload: DrawState) : Action(type= SET_DRAW) { var payload = payload }

    class SET_FAB_ICON_ACTION(payload: Int) : Action(type=SET_FAB_ICON) { var payload = payload }
    class SET_FAB_STATE_ACTION(payload: FabState) : Action(type=SET_FAB_STATE) { var payload = payload }

    class SEND_SNACKBAR_MESSAGE_ACTION(payload: SnackState) : Action(type=SEND_SNACKBAR_MESSAGE) { var payload = payload }

    class SET_LOGIN_PAGE_ACTION() : Action(type=SET_LOGIN_PAGE)
    class SET_HOME_PAGE_ACTION() : Action(type=SET_HOME_PAGE)
    class SET_SETTINGS_PAGE_ACTION() : Action(type=SET_SETTINGS_PAGE)
    class SET_ADD_TO_DAY_PAGE_ACTION() : Action(type=SET_ADD_TO_DAY_PAGE)

    class SET_LOADING_ACTION(payload: Boolean) : Action(type=SET_LOADING) { var payload = payload }
    class SET_DIALOG_ACTION(payload: DialogState) : Action(type= SET_DIALOG) { var payload = payload }

    override fun reduce(storeState: Any?, action: Action):Any? {
        var state: State? = storeState as State?
        if (null == state) {
            state = State()
        }

        return when(action.type) {
            SET_USER ->state.copy(user = (action as SET_USER_ACTION).payload)
            SET_DRAW ->state.copy(draw = (action as SET_DRAW_ACTION).payload)

            OPEN_FAB_MENU->state.copy(fab = state.fab.copy(isOpen = true))
            CLOSE_FAB_MENU->state.copy(fab = state.fab.copy(isOpen = false))
            TOGGLE_FAB_MENU->state.copy(fab = state.fab.copy(isOpen = !state.fab.isOpen))

            SET_FAB_ICON ->state.copy(fab = state.fab.copy(icon = (action as SET_FAB_ICON_ACTION).payload))
            SET_FAB_STATE ->state.copy(fab = (action as SET_FAB_STATE_ACTION).payload)

            SEND_SNACKBAR_MESSAGE ->state.copy(snack = (action as SEND_SNACKBAR_MESSAGE_ACTION).payload)

            SET_LOGIN_PAGE ->state.copy(
                    toolbar = ToolbarState(
                        title = "Login page",
                        showToolbar = false,
                        showBackBtn = false
                    ),
                    fab = FabState(showFab = false, isOpen = false),
                    page = PageState(selectedPage = LOGIN_PAGE)
            )
            SET_HOME_PAGE ->state.copy(
                    toolbar = ToolbarState(
                        title = "Home page",
                        showToolbar = true,
                        showBackBtn = false
                    ),
                    fab = FabState(showFab = true, isOpen = false),
                    page = PageState(selectedPage = HOME_PAGE)
            )
            SET_SETTINGS_PAGE->state.copy(
                    toolbar = ToolbarState(
                            title = "Settings page",
                            showToolbar = true,
                            showBackBtn = false
                    ),
                    fab = FabState(showFab = false, isOpen = false),
                    page = PageState(selectedPage = SETTING_PAGE)
            )
            SET_ADD_TO_DAY_PAGE->state.copy(
                    toolbar = ToolbarState(
                            title = "Add to Day page",
                            showToolbar = true,
                            showBackBtn = true
                    ),
                    fab = FabState(showFab = true, isOpen = false),
                    page = PageState(selectedPage = ADD_TO_DAY_PAGE)
            )

            SET_LOADING->state.copy(isLoading = (action as SET_LOADING_ACTION).payload)
            SET_DIALOG ->state.copy(dialog = (action as SET_DIALOG_ACTION).payload)
            else->state
        }
    }
}