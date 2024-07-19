<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <div class="flex items-center justify-between border-gray-200 bg-white">
        <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
            <div>
                <p class="text-sm text-gray-700">
                    Showing
                    <span class="font-medium p-1">${pagination.limit * (pagination.currentPage-1) + 1}</span>
                    to
                    <span class="font-medium p-1">
						<c:if test="${pagination.currentPage == pagination.lastPage}">
	                    	${pagination.length}
						</c:if>
						<c:if test="${pagination.currentPage != pagination.lastPage}">
	                    	${pagination.limit * pagination.currentPage}
						</c:if>
                    </span>
                    of
                    <span class="font-medium p-1">${pagination.length}</span>
                    results
                </p>
            </div>
            <div>
                <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
                    <c:if test="${pagination.currentPage-3 > 0}">
                        <a
                            href="${pagination.itemLink}1"
                            class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
                            >
                            1
                        </a>
                        <span class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-700 ring-1 ring-inset ring-gray-300 focus:outline-offset-0">
                            ...
                        </span>
                    </c:if>
                    <c:if test="${pagination.currentPage-2 > 0}">
                        <a
                            href="${pagination.itemLink}${pagination.currentPage-2}"
                            class="relative hidden items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 md:inline-flex"
                            >
                            ${pagination.currentPage-2}
                        </a>
                    </c:if>
                    <c:if test="${pagination.currentPage-1 > 0}">
                        <a
                            href="${pagination.itemLink}${pagination.currentPage-1}"
                            class="relative hidden items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 md:inline-flex"
                            >
                            ${pagination.currentPage-1}
                        </a>
                    </c:if>
                    <a
                        href="${pagination.itemLink}${pagination.currentPage}"
                        aria-current="page"
                        class="relative z-10 inline-flex items-center bg-indigo-600 px-4 py-2 text-sm font-semibold text-white focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        >
                        ${pagination.currentPage}
                    </a>
                    <c:if test="${pagination.currentPage + 1 <= pagination.lastPage}">
                        <a
                            href="${pagination.itemLink}${pagination.currentPage+1}"
                            class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
                            >
                            ${pagination.currentPage+1}
                        </a>
                    </c:if>
                    <c:if test="${pagination.currentPage + 2 <= pagination.lastPage}">
                        <a
                            href="${pagination.itemLink}${pagination.currentPage+2}"
                            class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
                            >
                            ${pagination.currentPage+2}
                        </a>
                    </c:if>
                    <c:if test="${pagination.currentPage + 3 <= pagination.lastPage}">
                        <span class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-700 ring-1 ring-inset ring-gray-300 focus:outline-offset-0">
                            ...
                        </span>
                        <a
                            href="${pagination.itemLink}${pagination.lastPage}"
                            class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
                            >
                            ${pagination.lastPage}
                        </a>
                    </c:if>
                </nav>
            </div>
        </div>
    </div>
</div>