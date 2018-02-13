class ToolbarState {
  title: string
  sideNavProps: object
  toolbarIcons: Array<object>

  constructor(title = "", toolbarIcons = []) {
    this.title = title
    this.toolbarIcons = toolbarIcons
  }
}

export default ToolbarState