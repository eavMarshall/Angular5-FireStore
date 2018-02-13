class FabState {
  isOpen: boolean
  icon: number
  showFab: boolean

  constructor(isOpen: boolean = false, icon: number = 0, showFab: boolean = true) {
    this.isOpen = isOpen
    this.icon = icon
    this.showFab = showFab
  }
}

export default FabState