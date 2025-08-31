export default function Page404() {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col items-center pt-12 px-4 ">
      {/* Robot SVG */}
      <div className="relative  max-w-md mb-6">
        <img src="/assets/notfoundrobot.png" alt="404 Robot" />
      </div>

      {/* Title */}
      <h2 className="text-5xl font-bold text-[#0C5F95] mb-6 ">
        Oups ! Page introuvable.
      </h2>

      {/* Description */}
      <div className="text-center text-gray-600 mb-10 leading-relaxed">
        <p>
          Il semble que vous ayez suivi un lien brisé ou que la page ait été{" "}
        </p>
        <p>déplacée. Ne vous inquiétez pas, nous vous aidons à retrouver</p>
        votre chemin.
      </div>

      {/* Return Button */}
      <button
        onClick={() => window.history.back()}
        className="bg-[#0C5F95] hover:bg-[#053A5F] text-white font-semibold py-3 px-6 rounded-lg transition-colors duration-200 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5"
      >
        Retour à la page précédente
      </button>
    </div>
  );
}
