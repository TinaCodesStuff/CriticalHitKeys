document.addEventListener('DOMContentLoaded', function () {
    var products = document.getElementsByClassName('prodotto');

    document.documentElement.classList.add('products-animate');

    function revealProducts() {
        var windowBottom = window.scrollY + window.innerHeight;

        for (var i = 0; i < products.length; i++) {
            var product = products[i];

            if (windowBottom > product.offsetTop + 80) {
                product.classList.add('product-visible');
            }
        }
    }

    window.addEventListener('scroll', revealProducts);
    window.addEventListener('resize', revealProducts);
    revealProducts();
});
