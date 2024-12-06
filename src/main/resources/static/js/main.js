(function ($) {
    "use strict";

    // Back to top button
    const $backToTop = $('.back-to-top');
    const $window = $(window);
    const $htmlBody = $('html, body');
    let isAnimating = false;

    // Xử lý hiển thị/ẩn button
    $window.scroll(function () {
        if ($window.scrollTop() > 300) {
            $backToTop.fadeIn(300);
        } else {
            $backToTop.fadeOut(300);
        }
    });

    // Xử lý sự kiện click
    $backToTop.on('click', function (e) {
        e.preventDefault();

        // Kiểm tra nếu đang animate thì không thực hiện thêm
        if (!isAnimating) {
            isAnimating = true;

            $htmlBody.animate({
                scrollTop: 0
            }, {
                duration: 600, // Giảm thời gian xuống
                easing: 'swing', // Sử dụng easing đơn giản
                complete: function () {
                    isAnimating = false;
                }
            });
        }
    });

    // Add scroll effect for navbar
    $(window).scroll(function () {
        if ($(window).scrollTop() > 50) {
            $('.custom-navbar').addClass('scrolled');
        } else {
            $('.custom-navbar').removeClass('scrolled');
        }
    });

    // Navigation active state handling
    $(document).ready(function () {
        // Get the current page path
        const currentPath = window.location.pathname;

        // Select all nav links
        $('.navbar-nav .nav-link').each(function () {
            const $link = $(this);
            const href = $link.attr('href');

            // Check if the current path matches the link's href
            // Use exact match for home, and startsWith for other routes
            if (
                (href === '/' && currentPath === '/') ||
                (href !== '/' && currentPath.startsWith(href))
            ) {
                // Remove active class from all nav items
                $link.closest('.navbar-nav').find('.nav-item').removeClass('active');

                // Add active class to the parent nav-item
                $link.closest('.nav-item').addClass('active');
            }
        });
    });

})(jQuery);