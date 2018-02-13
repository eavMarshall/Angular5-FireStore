class UserState {
  uid: string
  displayName: string
  email: string
  photoUrl: string

  constructor(uid = '', displayName = 'Anonymous', email = '', photoUrl = '') {
    this.uid = uid
    this.displayName = displayName
    this.email = email
    this.photoUrl = photoUrl
  }
}

export default UserState