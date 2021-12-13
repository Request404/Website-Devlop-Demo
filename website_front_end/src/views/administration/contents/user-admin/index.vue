<template>
  <div id="data_wrap">
    <header id="wrap_header">
      <div id="search_wrap">
        <Input v-model="keyword" size="large" search enter-button placeholder="请输入要查询的条件信息" @keyup.enter.native="searchForm" @click.native="searchForm"/>
      </div>
      <div id="add_drawer">
        <add/>
      </div>
    </header>
    <div id="add_table">
      <Table no-data-text="暂无数据" height="535" max-height="540" border :columns="columns" :data="data">
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row, index }" slot="state">
          <Button :disabled="operator === row.username" :type="row.state === 1?'warning':'success'"
                  size="small" style="margin-right: 5px"
                  @click="getOff(index)">{{ row.state === 1 ? '禁用' : '启用' }}
          </Button>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">查看
            <Icon type="md-clipboard"/>
          </Button>
          <span>
            <update :optionData="row"/>
          </span>
          <Button :disabled="operator === row.username" type="error" size="small" @click="remove(index)">删除
            <Icon type="md-trash"/>
          </Button>
        </template>
      </Table>
    </div>
    <Page style="margin-top: 1rem" :current="page" :total="total" @on-change="onPage"/>
  </div>
</template>

<script>
import Add from "./add";
import Update from "./update";

export default {
  name: "index",
  components: {Add, Update},
  data() {
    return {
      keyword: '',
      operator: '',
      columns: [
        {
          title: 'ID',
          key: 'id',
          width: 120,
          sortable: true
        },
        {
          title: '用户名',
          key: 'username',
          width: 135
        },
        {
          title: '性别',
          key: 'gender',
          width: 95,
          filters: [
            {
              label: '男',
              value: 1
            },
            {
              label: '女',
              value: 2
            }
          ],
          filterMultiple: false,
          filterMethod(value, row) {
            if (value === 1) {
              return row.gender === '男';
            } else if (value === 2) {
              return row.gender === '女';
            }
          }
        },
        {
          title: '手机号',
          key: 'telephone',
          width: 125,
        },
        {
          title: '邮箱',
          key: 'email',
          width: 180,
        },
        {
          title: '角色',
          key: 'role',
          width: 85,
          filters: [
            {
              label: 'root',
              value: 1
            },
            {
              label: 'admin',
              value: 2
            },
            {
              label: 'other',
              value: 3
            },
          ],
          filterMultiple: false,
          filterMethod(value, row) {
            if (value === 1) {
              return row.role === 'root';
            } else if (value === 2) {
              return row.gender === 'admin';
            } else if (value === 3) {
              return row.gender === 'other';
            }
          }
        },
        {
          title: '权限',
          key: 'authentication',
          width: 85
        },
        {
          title: '操作时间',
          key: 'operationTime',
          width: 170,
          sortable: true
        }, {
          title: '操作人',
          key: 'operationUser',
          width: 115
        }, {
          title: '状态',
          slot: 'state',
          width: 90,
          filters: [
            {
              label: '已禁用',
              value: 1
            },
            {
              label: '启用中',
              value: 2
            }
          ],
          filterMultiple: false,
          filterMethod(value, row) {
            if (value === 1) {
              return row.state === 0;
            } else if (value === 2) {
              return row.state === 1;
            }
          }
        },
        {
          title: '操作',
          slot: 'action',
          width: 235,
          align: 'center'
        }
      ],
      data: [],
      total: null,
      page: 1
    }
  },
  watch: {
    //watch可以监听data中指定数据的变化，然后触发对应的处理函数
    keyword: function (newVal) {
      if (newVal === '') {
        this.api.userSearchAllAdminUser(1, 10, newVal).then(rs => {
          this.data = rs.data.data.records
          this.total = rs.data.data.total + 1
        }).catch(err => {
          console.log(err);
        })
      }
    }
  },
  methods: {
    searchForm() {
      this.api.userSearchAllAdminUser(1, 10, this.keyword).then(rs => {
        this.data = rs.data.data.records
        this.total = rs.data.data.total + 1
      }).catch(err => {
        console.log(err);
      })
    },
    show(index) {
      this.$Modal.info({
        title: '用户信息',
        content: `<b>ID：</b>${this.data[index].id}<br>
          <b>用户名：</b>${this.data[index].username}<br>
          <b>密码：</b>${this.data[index].password.substr(0, 38) + '……'}<br>
          <b>昵称：</b>${this.data[index].nickName}<br>
          <b>性别：</b>${this.data[index].gender}<br>
          <b>手机号：</b>${this.data[index].telephone}<br>
          <b>邮箱：</b>${this.data[index].email}<br>
          <b>头像地址：</b>${this.data[index].headPortrait}<br>
          <b>角色：</b>${this.data[index].role}<br>
          <b>权限：</b>${this.data[index].authentication}<br>
          <b>状态：</b>${this.data[index].state === 1 ? '启用中' : '已被禁用'}<br>
          <b>操作时间：</b>${this.data[index].operationTime}<br>
          <b>操作用户：</b>${this.data[index].operationUser}<br>`
      })
    },
    remove(index) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.userRemove(this.data[index].id).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            location.reload();
          } else {
            this.$message.error(rs.data.msg);
          }
        }).catch(err => {
          console.log(err);
          this.$message.error('删除失败');
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    getOff(index) {
      this.$confirm(this.data[index].state === 1 ? '要将此用户禁用?' : '要将启用此用户?', '提示', {
        confirmButtonText: this.data[index].state === 1 ? '禁用?' : '启用',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.userForbidden(this.data[index].id).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
            location.reload();
          } else {
            this.$message.error(rs.data.msg);
          }
        }).catch(err => {
          console.log(err);
          this.$message.error('操作失败');
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.data[index].state === 1 ? '取消禁用?' : '取消启用'
        });
      });
    },
    onPage(page) {
      sessionStorage.setItem('adminUserPage', page);
      location.reload();
    }
  },
  created() {
    this.operator = JSON.parse(sessionStorage.getItem('website-user-info')).username
    if (sessionStorage.getItem('adminUserPage')) {
      this.page = parseInt(sessionStorage.getItem('adminUserPage'))
    }
    this.api.userSearchAllAdminUser(this.page, 10, '').then(rs => {
      this.data = rs.data.data.records
      this.total = rs.data.data.total + 1
    }).catch(err => {
      console.log(err);
    })
  }
}
</script>

<style scoped lang="scss">
#data_wrap {
  width: 100%;
  height: 90%;
  display: flex;
  flex-direction: column;
  align-items: center;

  #wrap_header {
    width: 98%;
    height: 15%;
    display: flex;
    justify-content: space-between;
    align-items: center;

    #search_wrap {
      width: 45%;
      display: flex;
      align-items: center;

    }

    #add_drawer {
      width: auto;
      height: auto;
    }
  }

  #add_table {
    width: 98%;
  }
}

.demo-drawer-footer {
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  background: #fff;
}
</style>
