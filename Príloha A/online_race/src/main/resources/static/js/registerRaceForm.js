class registerRace extends HTMLElement {
    constructor() {
        super();
        this.innerHTML='' +
            '<form class="my-jbox-form" method="POST">\n' +
            '  <label>\n' +
            '    Name:\n' +
            '    <input type="text" name="name" required>\n' +
            '  </label>\n' +
            '\n' +
            '  <label>\n' +
            '    Email:\n' +
            '    <input type="email" name="email" required>\n' +
            '  </label>\n' +
            '</form>'
    }
}
customElements.define('register-race', registerRace);