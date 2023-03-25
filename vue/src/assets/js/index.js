export default{
    map:{},
    $emit(name,params){
        if(this.map[name]==null){
            console.log("没有找到关于"+name+"的事件，无法触发")
        }else{
            this.map[name].detail=params;
            window.dispatchEvent(this.map[name]);
        }
    },
    $on(name,work){
        let myEvent = new Event(name);
        this.map[name]=myEvent;
        window.addEventListener(name, (event)=>{
            console.log('得到数据为：', event.detail);
            work(this.map[name].detail);
            return event.detail
        });
    }
}