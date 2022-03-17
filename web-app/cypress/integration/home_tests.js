
describe("Home", () => {
    it("Get Latest News", () => {
        cy.server();
        cy.route({
            method: 'GET',
            url: '/latest-news',
            status: 200,
            response: {
                rows: [
                    {
                        id: 4,
                        date: "10/27/20",
                        title: "New Listing 4",
                        synopsis: "Similique sunt in culpa qui officia deserunt mollitia animi, id est laborut dolorum fuga.harum quidem rerum facilis estexpedita distinctio."
                    },
                    {
                        id: 5,
                        date: "10/27/20",
                        title: "New Listing 5",
                        synopsis: "Similique sunt in culpa qui officia deserunt mollitia animi, id est laborut dolorum fuga.harum quidem rerum facilis estexpedita distinctio."
                    },
                    {
                        id: 6,
                        date: "10/27/20",
                        title: "New Listing 6",
                        synopsis: "Similique sunt in culpa qui officia deserunt mollitia animi, id est laborut dolorum fuga.harum quidem rerum facilis estexpedita distinctio."
                    }
                ]
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
        //
        // cy.get('input[name="login"]').type('badUsername')
        // cy.get('input[name="password"]').type('badPassword')
        //
        //
        // cy.contains('button', 'Login').click();
        //
        // cy.contains("Error: Request failed with status code 401").should('be.visible');
    });


})
