import {Loading} from 'element-ui';
import axios from 'axios'


export function request(config) {
  let loadingInstance = Loading.service({background: "rgba(220, 220, 220, .6)", fullscreen: true});
  const instance = axios.create({
    baseURL: 'http://localhost:88',
    timeout: 10000
  })

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