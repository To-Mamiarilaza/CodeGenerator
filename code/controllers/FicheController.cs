namespace controllers;

using using Microsoft.AspNetCore.Mvc;
using using Microsoft.EntityFrameworkCore;


[ApiController]
[Route("fiches")]
public class FicheController : ControllerBase {

    private readonly {dbServiceType} {dbServiceName};

    public FicheController({dbServiceType} {dbServiceName}) {
{dbServiceName} = {dbServiceName};
}

    [HttpGet]
    public async Task<ActionResult<IEnumerable<Fiche>>> GetAllFiches() {
        
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<Fiche>> GetFicheById(Integer idFiche) {
        
    }

    [HttpPost]
    public async Task<ActionResult<Fiche>> CreateFiche(Fiche fiche) {
        
    }

    [HttpPut("{id}")]
    public async Task<IActionResult> UpdateFiche(Integer idFiche, Fiche fiche) {
        
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteFiche(Integer idFiche) {
        
    }

}
