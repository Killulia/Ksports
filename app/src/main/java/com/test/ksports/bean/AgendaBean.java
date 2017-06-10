package com.test.ksports.bean;

import java.util.List;

/**
 * Created by kingwag on 2016/12/1.
 * 赛事实体类
 */

public class AgendaBean {

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {


        private String title;
        private StatuslistBean statuslist;
        private List<ListBean> list;
        private List<TeammatchBean> teammatch;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public StatuslistBean getStatuslist() {
            return statuslist;
        }

        public void setStatuslist(StatuslistBean statuslist) {
            this.statuslist = statuslist;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<TeammatchBean> getTeammatch() {
            return teammatch;
        }

        public void setTeammatch(List<TeammatchBean> teammatch) {
            this.teammatch = teammatch;
        }

        public static class StatuslistBean {
            /**
             * st0 : 未开赛
             * st1 : 直播中
             * st2 : 已结束
             */

            private String st0;
            private String st1;
            private String st2;

            public String getSt0() {
                return st0;
            }

            public void setSt0(String st0) {
                this.st0 = st0;
            }

            public String getSt1() {
                return st1;
            }

            public void setSt1(String st1) {
                this.st1 = st1;
            }

            public String getSt2() {
                return st2;
            }

            public void setSt2(String st2) {
                this.st2 = st2;
            }
        }

        public static class ListBean {


            private String title;
            private List<TrBean> tr;
            private List<BottomlinkBean> bottomlink;
            private List<LiveBean> live;
            private List<LivelinkBean> livelink;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<TrBean> getTr() {
                return tr;
            }

            public void setTr(List<TrBean> tr) {
                this.tr = tr;
            }

            public List<BottomlinkBean> getBottomlink() {
                return bottomlink;
            }

            public void setBottomlink(List<BottomlinkBean> bottomlink) {
                this.bottomlink = bottomlink;
            }

            public List<LiveBean> getLive() {
                return live;
            }

            public void setLive(List<LiveBean> live) {
                this.live = live;
            }

            public List<LivelinkBean> getLivelink() {
                return livelink;
            }

            public void setLivelink(List<LivelinkBean> livelink) {
                this.livelink = livelink;
            }

            public static class TrBean {
                /**
                 * time : 11/30 08:00
                 * player1 : 活塞
                 * player2 : 黄蜂
                 * player1logo : http://p3.qhmsg.com/t01a80ea5beeff44608.png?size=78x78
                 * player2logo : http://p7.qhmsg.com/t011105782c37d4f1c8.png?size=78x78
                 * player1logobig : http://p2.qhmsg.com/t01a80ea5beeff44608.png?size=78x78
                 * player2logobig : http://p7.qhmsg.com/t011105782c37d4f1c8.png?size=78x78
                 * player1url : http://kbs.sports.qq.com/kbsweb/teams.htm?tid=8
                 * player2url : http://kbs.sports.qq.com/kbsweb/teams.htm?tid=30
                 * link1url : http://sports.qq.com/kbsweb/game.htm?mid=100000:1468794
                 * m_link1url : http://j.www.haosou.com/?u=http%3A%2F%2Fsports.qq.com%2Fkbsweb%2Fkbsshare%2Fgame.htm%3Fmid%3D100000%3A1468794%26title%3D%25E6%25B4%25BB%25E5%25A1%259EVS%25E9%25BB%2584%25E8%259C%2582%26ptag%3D360.onebox.schedule.nba&m=d4b888&from=juhe&type=nba_new&juid=JHec7f8c8d36dc491090c0d41645b8dd1f
                 * link2text : 技术统计
                 * link2url : http://nba.stats.qq.com/nbascore/?mid=1468794
                 * m_link2url : http://j.www.haosou.com/?u=http%3A%2F%2Fsports.qq.com%2Fkbsweb%2Fkbsshare%2Fgame.htm%3Fmid%3D100000%3A1468794%26title%3D%25E6%25B4%25BB%25E5%25A1%259EVS%25E9%25BB%2584%25E8%259C%2582%26ptag%3D360.onebox.schedule.nba&m=d4b888&from=juhe&type=nba_new&juid=JHec7f8c8d36dc491090c0d41645b8dd1f
                 * status : 2
                 * score : 112-89
                 * link1text : 视频集锦
                 */

                private String time;
                private String player1;
                private String player2;
                private String player1logo;
                private String player2logo;
                private String player1logobig;
                private String player2logobig;
                private String player1url;
                private String player2url;
                private String link1url;
                private String m_link1url;
                private String link2text;
                private String link2url;
                private String m_link2url;
                private int status;
                private String score;
                private String link1text;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getPlayer1() {
                    return player1;
                }

                public void setPlayer1(String player1) {
                    this.player1 = player1;
                }

                public String getPlayer2() {
                    return player2;
                }

                public void setPlayer2(String player2) {
                    this.player2 = player2;
                }

                public String getPlayer1logo() {
                    return player1logo;
                }

                public void setPlayer1logo(String player1logo) {
                    this.player1logo = player1logo;
                }

                public String getPlayer2logo() {
                    return player2logo;
                }

                public void setPlayer2logo(String player2logo) {
                    this.player2logo = player2logo;
                }

                public String getPlayer1logobig() {
                    return player1logobig;
                }

                public void setPlayer1logobig(String player1logobig) {
                    this.player1logobig = player1logobig;
                }

                public String getPlayer2logobig() {
                    return player2logobig;
                }

                public void setPlayer2logobig(String player2logobig) {
                    this.player2logobig = player2logobig;
                }

                public String getPlayer1url() {
                    return player1url;
                }

                public void setPlayer1url(String player1url) {
                    this.player1url = player1url;
                }

                public String getPlayer2url() {
                    return player2url;
                }

                public void setPlayer2url(String player2url) {
                    this.player2url = player2url;
                }

                public String getLink1url() {
                    return link1url;
                }

                public void setLink1url(String link1url) {
                    this.link1url = link1url;
                }

                public String getM_link1url() {
                    return m_link1url;
                }

                public void setM_link1url(String m_link1url) {
                    this.m_link1url = m_link1url;
                }

                public String getLink2text() {
                    return link2text;
                }

                public void setLink2text(String link2text) {
                    this.link2text = link2text;
                }

                public String getLink2url() {
                    return link2url;
                }

                public void setLink2url(String link2url) {
                    this.link2url = link2url;
                }

                public String getM_link2url() {
                    return m_link2url;
                }

                public void setM_link2url(String m_link2url) {
                    this.m_link2url = m_link2url;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getLink1text() {
                    return link1text;
                }

                public void setLink1text(String link1text) {
                    this.link1text = link1text;
                }
            }

            public static class BottomlinkBean {
                /**
                 * text : 常规赛赛程
                 * url : http://sports.qq.com/nba/schedule/?ptag=360.onebox.schedule.nba
                 */

                private String text;
                private String url;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class LiveBean {
                /**
                 * title : 11:00开赛 比赛结束
                 * player1 : 步行者
                 * player2 : 开拓者
                 * player1info : 胜9负10东部10名
                 * player2info : 胜10负10西部8名
                 * player1logobig : http://p6.qhmsg.com/t01a540cb8f7dae8bd7.png?size=78x78
                 * player2logobig : http://p6.qhmsg.com/t01fdabb4fd4f401729.png?size=78x78
                 * player1url : http://kbs.sports.qq.com/kbsweb/teams.htm?tid=11
                 * player2url : http://kbs.sports.qq.com/kbsweb/teams.htm?tid=22
                 * player1location : (客)
                 * player2location : (主)
                 * status : 2
                 * score : 109-131
                 * liveurl :
                 */

                private String title;
                private String player1;
                private String player2;
                private String player1info;
                private String player2info;
                private String player1logobig;
                private String player2logobig;
                private String player1url;
                private String player2url;
                private String player1location;
                private String player2location;
                private int status;
                private String score;
                private String liveurl;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getPlayer1() {
                    return player1;
                }

                public void setPlayer1(String player1) {
                    this.player1 = player1;
                }

                public String getPlayer2() {
                    return player2;
                }

                public void setPlayer2(String player2) {
                    this.player2 = player2;
                }

                public String getPlayer1info() {
                    return player1info;
                }

                public void setPlayer1info(String player1info) {
                    this.player1info = player1info;
                }

                public String getPlayer2info() {
                    return player2info;
                }

                public void setPlayer2info(String player2info) {
                    this.player2info = player2info;
                }

                public String getPlayer1logobig() {
                    return player1logobig;
                }

                public void setPlayer1logobig(String player1logobig) {
                    this.player1logobig = player1logobig;
                }

                public String getPlayer2logobig() {
                    return player2logobig;
                }

                public void setPlayer2logobig(String player2logobig) {
                    this.player2logobig = player2logobig;
                }

                public String getPlayer1url() {
                    return player1url;
                }

                public void setPlayer1url(String player1url) {
                    this.player1url = player1url;
                }

                public String getPlayer2url() {
                    return player2url;
                }

                public void setPlayer2url(String player2url) {
                    this.player2url = player2url;
                }

                public String getPlayer1location() {
                    return player1location;
                }

                public void setPlayer1location(String player1location) {
                    this.player1location = player1location;
                }

                public String getPlayer2location() {
                    return player2location;
                }

                public void setPlayer2location(String player2location) {
                    this.player2location = player2location;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getLiveurl() {
                    return liveurl;
                }

                public void setLiveurl(String liveurl) {
                    this.liveurl = liveurl;
                }
            }

            public static class LivelinkBean {
                /**
                 * text : 视频集锦
                 * url : http://sports.qq.com/kbsweb/game.htm?mid=100000:1468809
                 */

                private String text;
                private String url;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class TeammatchBean {
            /**
             * name : 老鹰
             * url : http://sports.qq.com/nba/schedule/?team=hawks
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
