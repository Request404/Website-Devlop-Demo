import {request} from '../api/request'

export default {
  login(context, payload) {
    console.log(payload.username);
    return request({
      method: 'post',
      url: '/admin/login',
      data: {
        username: payload.username,
        password: payload.password
      }
    })
  },
}
