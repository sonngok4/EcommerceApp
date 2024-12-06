document.addEventListener('DOMContentLoaded', function () {
    // Get filter elements
    const categoryCheckboxes = document.querySelectorAll('input[name="category"]');
    const brandCheckboxes = document.querySelectorAll('input[name="brand"]');
    const minPriceInput = document.getElementById('priceMin');
    const maxPriceInput = document.getElementById('priceMax');
    const sortSelect = document.querySelector('select');
    const displayCountRadios = document.querySelectorAll('input[name="displayCount"]');
    const viewModeRadios = document.querySelectorAll('input[name="viewMode"]');
    const applyFiltersButton = document.getElementById('applyFilters');

    // Apply Filters Function
    function applyFilters() {
        // Get selected categories
        const selectedCategories = Array.from(categoryCheckboxes)
            .filter(cb => cb.checked)
            .map(cb => cb.value);

        // Get selected brands
        const selectedBrands = Array.from(brandCheckboxes)
            .filter(cb => cb.checked)
            .map(cb => cb.value);

        // Get price range
        const minPrice = minPriceInput.value ? parseFloat(minPriceInput.value) : 0;
        const maxPrice = maxPriceInput.value ? parseFloat(maxPriceInput.value) : Infinity;

        // Get sort option
        const sortOption = sortSelect.value;

        // Get display count
        const displayCount = document.querySelector('input[name="displayCount"]:checked').value;

        // Get view mode
        const viewMode = document.querySelector('input[name="viewMode"]:checked').value;

        // Here you would typically make an AJAX call to your backend 
        // to filter and sort products based on these parameters
        console.log({
            categories: selectedCategories,
            brands: selectedBrands,
            priceRange: { min: minPrice, max: maxPrice },
            sortBy: sortOption,
            displayCount: displayCount,
            viewMode: viewMode
        });

        // Example of how you might filter products client-side (for demonstration)
        const products = document.querySelectorAll('.product-item');
        products.forEach(product => {
            const price = parseFloat(product.querySelector('.product-price').textContent.replace('$', ''));
            const category = product.querySelector('.product-title').textContent.toLowerCase();

            const matchesPrice = price >= minPrice && price <= maxPrice;
            const matchesCategory = selectedCategories.length === 0 ||
                selectedCategories.some(cat => category.includes(cat.toLowerCase()));

            product.style.display = matchesPrice && matchesCategory ? 'block' : 'none';
        });
    }

    // Add event listeners to filter controls
    applyFiltersButton.addEventListener('click', applyFilters);
});