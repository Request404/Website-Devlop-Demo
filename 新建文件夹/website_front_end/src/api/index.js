import {request} from './request'

function Api() {

}

let api = new Api();

/** ========================Admin============================ **/

Api.prototype.adminAdd = function (admin) {
  return request({
    method: 'put',
    url: '/admin/add',
    data: {
      id: admin.id,
      username: admin.username,
      password: admin.password,
      name: admin.name,
      telephone: admin.telephone,
      email: admin.email,
      identity: admin.identity,
      operationTime: admin.operationTime
    }
  })
};

Api.prototype.adminUpdate = function (admin) {
  return request({
    method: 'post',
    url: '/admin/update',
    data: {
      id: admin.id,
      username: admin.username,
      password: admin.password,
      name: admin.name,
      telephone: admin.telephone,
      email: admin.email,
      identity: admin.identity,
      operationTime: admin.operationTime
    }
  })
};

Api.prototype.adminRemove = function (id) {
  return request({
    method: 'delete',
    url: '/admin/remove',
    params: {
      id: id
    }
  })
};

Api.prototype.adminFind = function (condition) {
  return request({
    method: 'get',
    url: '/admin/find',
    params: {
      condition: condition
    }
  })
};

Api.prototype.adminList = function () {
  return request({
    method: 'get',
    url: '/admin/list'
  })
};

/** ========================Staff============================ **/

Api.prototype.staffAdd = function (staff) {
  return request({
    method: 'put',
    url: '/staff/add',
    data: {
      sname: staff.sName,
      sgender: staff.sGender,
      sage: staff.sAge * 1,
      stelephone: staff.sTelephone,
      semail: staff.sEmail,
      censusRegister: staff.censusRegister,
      politicsStatus: staff.politicsStatus,
      healthStatus: staff.healthStatus,
      contractId: staff.contractId,
      contract: {
        csection: staff.contract.cSection,
        cjob: staff.contract.cJob,
        csalary: staff.contract.cSalary,
        cstart: staff.contract.cStart.toISOString().slice(0, 10),
        cend: staff.contract.cEnd.toISOString().slice(0, 10)
      }
    }
  })
};

Api.prototype.staffUpdate = function (staff) {
  return request({
    method: 'post',
    url: '/staff/update',
    data: {
      id: staff.id,
      sname: staff.sName,
      sgender: staff.sGender,
      sage: staff.sAge,
      stelephone: staff.sTelephone,
      semail: staff.sEmail,
      censusRegister: staff.censusRegister,
      politicsStatus: staff.politicsStatus,
      healthStatus: staff.healthStatus,
      contractId: staff.contractId,
    }
  })
};

Api.prototype.staffRemove = function (id) {
  return request({
    method: 'delete',
    url: '/staff/remove',
    params: {
      id: id
    }
  })
};

Api.prototype.staffFind = function (condition) {
  return request({
    method: 'get',
    url: '/staff/find',
    params: {
      condition: condition
    }
  })
};

Api.prototype.staffList = function () {
  return request({
    method: 'get',
    url: '/staff/list'
  })
};

/** ========================Contract============================ **/

Api.prototype.contractAdd = function (contract) {
  return request({
    method: 'put',
    url: '/contract/add',
    data: {
      contract: contract
    }
  })
};

Api.prototype.contractUpdate = function (contract) {
  return request({
    method: 'post',
    url: '/contract/update',
    data: {
      id: contract.id,
      sname: contract.sName,
      csection: contract.cSection,
      cjob: contract.cJob,
      csalary: contract.cSalary,
      cstart: contract.cStart.toISOString().slice(0, 10),
      cend: contract.cEnd.toISOString().slice(0, 10),
    }
  })
};

Api.prototype.contractRemove = function (id) {
  return request({
    method: 'delete',
    url: '/contract/remove',
    params: {
      id: id
    }
  })
};

Api.prototype.contractFind = function (condition) {
  return request({
    method: 'get',
    url: '/contract/find',
    params: {
      id: condition
    }
  })
};

Api.prototype.contractList = function () {
  return request({
    method: 'get',
    url: '/contract/list'
  })
};

/** ========================Workday============================ **/

Api.prototype.workdayAdd = function (workday) {
  return request({
    method: 'put',
    url: '/workday/add',
    data: {
      workday: workday
    }
  })
};

Api.prototype.workdayUpdate = function (workday) {
  return request({
    method: 'post',
    url: '/workday/update',
    data: {
      id: workday.id,
      sid: workday.sId,
      sname: workday.sName,
      wstart: workday.wStartDate.toISOString().slice(0, 10)+" "+workday.wStartTime,
      wend: workday.wEndDate.toISOString().slice(0, 10)+" "+workday.wEndTime,
    }
  })
};

// Api.prototype.workdayRemove = function (id) {
//   return request({
//     method: 'delete',
//     url: '/workday/remove',
//     params: {
//       id: id
//     }
//   })
// };

Api.prototype.workdayFind = function (condition) {
  return request({
    method: 'get',
    url: '/workday/find',
    params: {
      sid: condition
    }
  })
};

Api.prototype.workdayList = function () {
  return request({
    method: 'get',
    url: '/workday/list'
  })
};

/** ========================Overtime============================ **/

Api.prototype.overtimeAdd = function (overtime) {
  return request({
    method: 'put',
    url: '/overtime/add',
    data: {
      overtime: overtime
    }
  })
};

Api.prototype.overtimeUpdate = function (overtime) {
  return request({
    method: 'post',
    url: '/overtime/update',
    data: {
      overtime: overtime
    }
  })
};

Api.prototype.overtimeRemove = function (id) {
  return request({
    method: 'delete',
    url: '/overtime/remove',
    params: {
      id: id
    }
  })
};

Api.prototype.overtimeFind = function (condition) {
  return request({
    method: 'get',
    url: '/overtime/find',
    params: {
      condition: condition
    }
  })
};

Api.prototype.overtimeList = function () {
  return request({
    method: 'get',
    url: '/overtime/list'
  })
};

/** ========================Evection============================ **/

Api.prototype.evectionAdd = function (evection) {
  return request({
    method: 'put',
    url: '/evection/add',
    data: {
      evection: evection
    }
  })
};

Api.prototype.evectionUpdate = function (evection) {
  return request({
    method: 'post',
    url: '/evection/update',
    data: {
      evection: evection
    }
  })
};

Api.prototype.evectionRemove = function (id) {
  return request({
    method: 'delete',
    url: '/evection/remove',
    params: {
      id: id
    }
  })
};

Api.prototype.evectionFind = function (condition) {
  return request({
    method: 'get',
    url: '/evection/find',
    params: {
      condition: condition
    }
  })
};

Api.prototype.evectionList = function () {
  return request({
    method: 'get',
    url: '/evection/list'
  })
};

/** ========================Salaries============================ **/

Api.prototype.salariesAdd = function (salaries) {
  return request({
    method: 'put',
    url: '/salaries/add',
    data: {
      salaries: salaries
    }
  })
};

Api.prototype.salariesUpdate = function (salaries) {
  return request({
    method: 'post',
    url: '/salaries/update',
    data: {
      salaries: salaries
    }
  })
};

Api.prototype.salariesRemove = function (id) {
  return request({
    method: 'delete',
    url: '/salaries/remove',
    params: {
      id: id
    }
  })
};

Api.prototype.salariesFind = function (condition) {
  return request({
    method: 'get',
    url: '/salaries/find',
    params: {
      condition: condition
    }
  })
};

Api.prototype.salariesList = function () {
  return request({
    method: 'get',
    url: '/salaries/list'
  })
};


export default api;