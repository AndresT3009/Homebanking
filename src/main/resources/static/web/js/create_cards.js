const app = Vue.createApp({
    data(){
        return{
            data_general: [],
            cards:[],
            client: [],
            accounts: [],
            loans:[],
            firstName:"",
            lastName:"",
            detalle_cuentas:[],
            date:"",
            card_type:"",
            card_color:""
        }
    },

    created() {
        this.loadData();
    },
    
    methods: {
        loadData() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.data_general = response
                    this.client = this.data_general.data
                    this.accounts = this.client.accounts
                    this.loans = this.client.clientLoans
                    this.cards = this.data_general.data.cards
                    this.credit_cards=this.cards.filter((card)=>(card.type=="CREDIT"))
                    this.debit_cards=this.cards.filter((card)=>(card.type=="DEBIT"))
                })
        },
        modificarSaldo(saldo){
            return new Intl.NumberFormat("en-US", {style: "currency", currency:"USD"}).format(saldo);
        },
        logOut(){
            return axios.post('/api/logout')
            .then(response=> window.location.href = "http://localhost:8080/web/index.html")
        },
        create_card(){
            let type=this.card_type
            let color=this.card_color
            return axios.post('/api/clients/current/cards',`cardType=${type}&cardColor=${color}`)
            .then(response=> window.location.href = "http://localhost:8080/web/cards.html")
            .catch(function(error){
                if(error.response.data=="Missing Card Type"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Please choose card type'})
                }
                if(error.response.data=="Missing Last Name"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Last Name field is missing'})
                }
                if(error.response.data=="Missing Email"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Email field is missing'})
                }
                if(error.response.data=="Missing Password"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Password field is missing'})
                }
                if(error.response.data=="Name already in use"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Username is already in use'})
                }
            })
        },
        
    },
    computed:{        
    },
})
app.mount('#app')