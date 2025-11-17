<script>
    document.getElementById("editForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const form = e.target;
        const formData = new FormData(form);

        const res = await fetch(form.action, {
            method: "PUT",
            body: formData,
        });

        const result = await res.json();
        alert(result.message);

        if (result.success) {
            const boardSeq = "${bdto.boardContentSeq}";
            location.href = `${pageContext.request.contextPath}/crewboard/view?boardContentSeq=\${boardSeq}`;
        }
    });
</script>
