
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;

[ApiController]
[Route("/fiches")]
public class FicheController : ControllerBase {
    
    private readonly DatabaseContext _context;
    
    public FicheController(DatabaseContext context) {
        _context = context;
    }
    
    [HttpGet]
    public async Task<ActionResult<IEnumerable<Fiche>>> GetAllFiches() {
        return await _context.Fiches.ToListAsync();
    }
    
    [HttpGet("{idFiche}")]
    public async Task<ActionResult<Fiche>> GetFicheById(int idFiche) {
        var fiche = await _context.Fiches.FirstOrDefaultAsync(f => f.IdFiche == idFiche);
        
        if(fiche == null) {
            return NotFound();
        }
        
        return fiche;
    }
    
    [HttpPost]
    public async Task<ActionResult<Fiche>> CreateFiche(Fiche fiche) {
        _context.Fiches.Add(fiche);
        await _context.SaveChangesAsync();
        
        return CreatedAtAction(nameof(GetFicheById), new { idFiche = fiche.IdFiche }, fiche);
    }
    
    [HttpPut("{idFiche}")]
    public async Task<IActionResult> UpdateFiche(int idFiche, Fiche fiche) {
        if (idFiche != fiche.IdFiche)
        {
            return BadRequest();
        }
        
        _context.Entry(fiche).State = EntityState.Modified;
        
        try
        {
            await _context.SaveChangesAsync();
        }
        catch(DbUpdateConcurrencyException)
        {
            throw;
        }
        
        return NoContent();
    }
    
    [HttpDelete("{idFiche}")]
    public async Task<IActionResult> DeleteFiche(int idFiche) {
        var fiche = await _context.Fiches.FindAsync(idFiche);
        if (fiche == null)
        {
            return NotFound();
        }
        
        _context.Fiches.Remove(fiche);
        await _context.SaveChangesAsync();
        
        return NoContent();
    }
    
}
