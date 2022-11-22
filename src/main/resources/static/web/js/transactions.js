const app = Vue.createApp({
    data(){
        return{
            data_general: [],
            client: [],
            accounts: [],
            firstName:"",
            lastName:"",
            transactions:[],
            id: new URLSearchParams(location.search).get('id'),
        }
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get("/api/account/"+this.id)
                .then(response => {
                    this.data_general = response
                    this.accounts = this.data_general.data
                    this.transactions=this.accounts.transactions
                    this.transactions.sort((a,b)=>b.id-a.id)
                })
        },

        logOut(){
            return axios.post('/api/logout')
            .then(response=> window.location.href = "http://localhost:8080/web/index.html")
        },
        
        modificarSaldo(saldo){
            return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(saldo);
        },

        datemodified(date){
            return new Date(date).toLocaleDateString('es-co', {year:"numeric", month:"short", day:"numeric"})
        },
    },
    computed:{
    },
})
app.mount('#app')