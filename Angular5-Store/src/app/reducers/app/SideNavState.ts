interface MenuItem {
  title: string
  id: string
}

class SideNavState {
  title: string
  isOpen: boolean
  selectedid: string
  menuList: Array<MenuItem>

  constructor(
    isOpen: boolean = false,
    selectedid: string = 'app/day',
    menuList: Array<MenuItem> = [
      { title: 'Day', id: 'app/day' },
      { title: 'Settings', id: 'app/settings' }
    ],
    title = 'Angular 5 app'
  ) {
    this.isOpen = isOpen
    this.menuList = menuList
    this.title = title
  }
}

export default SideNavState