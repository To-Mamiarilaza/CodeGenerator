
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;

[ApiController]
[Route("/typeLanguages")]
public class TypeLanguageController : ControllerBase {
    
    private readonly DatabaseContext _context;
    
    public TypeLanguageController(DatabaseContext context) {
        _context = context;
    }
    
    [HttpGet]
    public async Task<ActionResult<IEnumerable<TypeLanguage>>> GetAllTypeLanguages() {
        return await _context.TypeLanguages.ToListAsync();
    }
    
    [HttpGet("{idTypeLanguage}")]
    public async Task<ActionResult<TypeLanguage>> GetTypeLanguageById(int idTypeLanguage) {
        var typeLanguage = await _context.TypeLanguages.FirstOrDefaultAsync(t => t.IdTypeLanguage == idTypeLanguage);
        
        if(typeLanguage == null) {
            return NotFound();
        }
        
        return typeLanguage;
    }
    
    [HttpPost]
    public async Task<ActionResult<TypeLanguage>> CreateTypeLanguage(TypeLanguage typeLanguage) {
        _context.TypeLanguages.Add(typeLanguage);
        await _context.SaveChangesAsync();
        
        return CreatedAtAction(nameof(GetTypeLanguageById), new { idTypeLanguage = typeLanguage.IdTypeLanguage }, typeLanguage);
    }
    
    [HttpPut("{idTypeLanguage}")]
    public async Task<IActionResult> UpdateTypeLanguage(int idTypeLanguage, TypeLanguage typeLanguage) {
        if (idTypeLanguage != typeLanguage.IdTypeLanguage)
        {
            return BadRequest();
        }
        
        _context.Entry(typeLanguage).State = EntityState.Modified;
        
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
    
    [HttpDelete("{idTypeLanguage}")]
    public async Task<IActionResult> DeleteTypeLanguage(int idTypeLanguage) {
        var typeLanguage = await _context.TypeLanguages.FindAsync(idTypeLanguage);
        if (typeLanguage == null)
        {
            return NotFound();
        }
        
        _context.TypeLanguages.Remove(typeLanguage);
        await _context.SaveChangesAsync();
        
        return NoContent();
    }
    
}
