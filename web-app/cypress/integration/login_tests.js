
describe("Login Overlay", () => {

    it("Show Login In Overlay", () => {

        cy.visit("/")

        cy.contains('button', 'Log In').click()

        cy.contains('button', 'Sign In').should('be.visible')

    });

    it("Show Sign Up Overlay", () => {

        cy.visit("/")

        cy.contains('button', 'Sign Up').click()

        cy.contains('button', 'Register').should('be.visible')

    });

    it("Should show an invalid username or password message on failure", () => {
        cy.server();
        cy.route({
            method: 'POST',
            url: '/api/auth/v1/token',
            status: 401,
            response: {
            },
            delay: 500,
            // headers: {
            //     'X-Token': null
            // },
            onRequest: (xhr) => {
                // do something with the
                // raw XHR object when the
                // request initially goes out
            },
            onResponse: (xhr) => {
                // do something with the
                // raw XHR object when the
                // response comes back
            }
        })


        cy.visit("/")

        cy.contains('button', 'Log In').click()

        cy.get('input[name="login"]').type('bad@username.com')
        cy.get('input[name="password"]').type('badPassword')

        cy.contains('button', 'Sign In').click()

    });



})
