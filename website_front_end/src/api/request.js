import {Loading} from 'element-ui';
import axios from 'axios'

let baseUrl = 'http://127.0.0.1:8081/api';

const instance = axios.create({
  baseURL: baseUrl,
  timeout: 60000
})

const initialLogin = axios.create({
  baseURL: baseUrl,
  timeout: 60000
})

instance.interceptors.request.use(function (config) {
  if (sessionStorage.getItem('website-user')) {
    let sessionUser = JSON.parse(sessionStorage.getItem('website-user'));
    if (parseInt(sessionUser.tokenPassTime) > new Date().getTime() || sessionUser.accessToken === '') {
      config.headers.Authorization = sessionUser.accessToken;
    } else {
      initialLogin({
        method: 'post',
        url: 'user/account/login',
        params: {
          username: sessionUser.username,
          password: sessionUser.password,
        }
      }).then(rs => {
        config.headers.Authorization = rs.data.token;
        let user = {
          username: sessionUser.username,
          password: sessionUser.password,
          accessToken: rs.data.token,
          tokenPassTime: new Date().getTime() + 28 * 60 * 1000
        }
        sessionStorage.setItem("website-user", JSON.stringify(user));
      }).catch(err => {
        console.log(err);
      })
    }
  }
  return config;
}, function (err) {
  return Promise.reject(err);
});

export function request(config) {
  let loadingInstance = Loading.service({background: "rgba(220, 220, 220, .6)", text: "加载中...", fullscreen: true});
  loadingInstance.$message
  return instance(config).then(rs => {
    if (rs) {
      loadingInstance.close()
    }
    return rs;
  }).catch(err => {
    loadingInstance.close()
    return err
  })
}
