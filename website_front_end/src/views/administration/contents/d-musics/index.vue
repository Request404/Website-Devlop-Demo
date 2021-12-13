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
          <Button :type="row.state === 1?'warning':'success'" size="small" style="margin-right: 5px"
                  @click="getOff(index)">{{ row.state === 1 ? '下架' : '上架' }}
          </Button>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">查看
            <Icon type="md-clipboard"/>
          </Button>
          <span>
            <update :optionData="row"/>
          </span>
          <Button type="error" size="small" @click="remove(index)">删除
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
      columns: [
        {
          title: 'ID',
          key: 'id',
          width: 115,
          sortable: true
        },
        {
          title: '歌曲名',
          key: 'name',
          width: 140
        },
        {
          title: '专辑',
          key: 'album',
          width: 130
        },
        {
          title: '发行地区',
          key: 'country',
          width: 95
        },
        {
          title: '类型',
          key: 'type',
          width: 85,
        },
        {
          title: '演唱者',
          key: 'singer',
          width: 110,
        },
        {
          title: '长度/秒',
          key: 'minutes',
          width: 110,
          sortable: true
        },{
          title: '发行年份',
          key: 'releaseYear',
          width: 95,
        },
        {
          title: '操作时间',
          key: 'operationTime',
          width: 115,
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
              label: '已下架',
              value: 1
            },
            {
              label: '已上架',
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
        this.api.dMusicGetAllMusics(1, 10, newVal).then(rs => {
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
      this.api.dMusicGetAllMusics(1, 10, this.keyword).then(rs => {
        this.data = rs.data.data.records
        this.total = rs.data.data.total + 1
      }).catch(err => {
        console.log(err);
      })
    },
    show(index) {
      this.$Modal.info({
        title: '音乐信息',
        content: `<b>ID：</b>${this.data[index].id}<br>
          <b>歌曲名：</b>${this.data[index].name}<br>
          <b>专辑：</b>${this.data[index].album}<br>
          <b>类型：</b>${this.data[index].type}<br>
          <b>演唱者：</b>${this.data[index].singer}<br>
          <b>作词人：</b>${this.data[index].writeWords}<br>
          <b>作曲人：</b>${this.data[index].compose}<br>
          <b>发行地区：</b>${this.data[index].country}<br>
          <b>发行年份：</b>${this.data[index].releaseYear}<br>
          <b>长度（秒）：</b>${this.data[index].minutes}<br>
          <b>专辑封面地址：</b>${this.data[index].albumAvatarUrl}<br>
          <b>歌手封面地址：</b>${this.data[index].singerAvatarUrl}<br>
          <b>操作时间：</b>${this.data[index].operationTime}<br>
          <b>操作人：</b>${this.data[index].operationUser}<br>
          <b>歌词：</b>${this.data[index].lyric.substring(0, 100) + "……"}<br>`
      })
    },
    remove(index) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.dMusicRemove(this.data[index].id).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            location.reload();
          } else {
            this.$message.error('删除失败');
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
      this.$confirm(this.data[index].state === 1 ? '要将此列下架?' : '要将此列重新上架?', '提示', {
        confirmButtonText: this.data[index].state === 1 ? '下架?' : '上架',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.dMusicGetOff(this.data[index].id).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
            location.reload();
          } else {
            this.$message.error('操作失败');
          }
        }).catch(err => {
          console.log(err);
          this.$message.error('操作失败');
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.data[index].state === 1 ? '取消下架?' : '取消上架'
        });
      });
    },
    onPage(page) {
      sessionStorage.setItem('dMusicsPage', page);
      location.reload();
    }
  },
  created() {
    if (sessionStorage.getItem('dMusicsPage')) {
      this.page = parseInt(sessionStorage.getItem('dMusicsPage'))
    }
    this.api.dMusicGetAllMusics(this.page, 10, '').then(rs => {
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
