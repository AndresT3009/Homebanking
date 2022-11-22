const app = Vue.createApp({
    data() {
        return {
            data_general: [],
            cards: [],
            credit_cards: [],
            debit_cards: [],
            firstName: "",
            lastName: "",
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
                    this.cards = this.data_general.data.cards
                    this.credit_cards = this.cards.filter((card) => (card.type == "CREDIT"))
                    this.debit_cards = this.cards.filter((card) => (card.type == "DEBIT"))
                })
                .catch(function(error){
                    if(error.response.data=="Your card is overdue"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'Account type field is missing'})
                    }
                })  

        },



        logOut() {
            return axios.post('/api/logout')
                .then(response => window.location.href = "http://localhost:8080/web/index.html")
        },

        datemodified(date){
            return new Date(date).toLocaleDateString('es-co', {year:"numeric", month:"short", day:"numeric"})
        },
    },
    computed: {
    },

})
app.mount('#app')