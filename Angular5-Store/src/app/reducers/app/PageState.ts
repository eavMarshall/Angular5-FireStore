class PageState {
  width: number
  height: number
  isMobile: boolean = false

  constructor(width = 300, height = 700) {
    this.width = width
    this.height = height
    this.isMobile = width < 768 ? true : false
  }
}

export default PageState